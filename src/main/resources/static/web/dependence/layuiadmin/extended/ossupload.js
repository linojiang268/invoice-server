layui.define(['upload'], function(exports) {
    var upload = layui.upload,
        ossupload = {
            VERSION: '1.0.0'
        };

    /**
     * request from application server for upload policy
     *
     * @param {String} uri  uri on app server to retrieve oss upload policy from
     * @returns {Object} policy object with props as below:
     * - host        oss host, e.g., http://post-test.oss-cn-hangzhou.aliyuncs.com
     * - policy      base64 encoded policy
     * - accessid    access key id. refer 'AccessKey' section in the following link:
     *               https://help.aliyun.com/document_detail/31827.html?spm=a2c4g.11186623.2.9.tH2D3P
     * - signature   signed policy
     * - expire      expiry time. Measured in the number of seconds since the Unix Epoch.
     * - callback    callback url to app server. refer the link below to get how it works:
     *               https://help.aliyun.com/document_detail/31927.html?spm=a2c4g.11186623.2.5.41o9zl
     * - dir         directory prefix for uploading files to oss(forming a part of the final key)
     */
    function requestForPolicy(uri) {
        var xmlHttp = createXmlHttpRequest();
        if (xmlHttp != null) {
            // we'll use synchronous request to prevent unnecessary duplicated request
            xmlHttp.open('GET', uri, false/* sync-ed */);
            xmlHttp.send(null);

            return JSON.parse(xmlHttp.responseText);
        }


        // Since I don't believe this line of code can be reached, I won't handle this case at all.
        alert("Your browser does not support XmlHttp.");
    }

    // old-school/low-level way to create XMLHttpRequest object
    function createXmlHttpRequest() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        }
        if (window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
        }

        console && console.debug('Cannot believe this, have no XMLHttpRequest support?');
        return null;
    }

    // simply apply before and after advice on given function
    function applyAdvice(func, before, after, context) {
        return function () {
            var args = Array.prototype.slice.call(arguments);

            // apply before advice
            before && before.apply(context, args);
            // proceed the function
            var ret = func.apply(context, args);
            // apply after advice
            after && after.apply(context, args);

            return ret;
        };
    }


    /**
     * grab property form given object (the options param), and get that property removed from the object.
     * @param {Object} instance   object instance (cannot be null/undefined)
     * @param {String} prop       prop name
     * @returns {*}               value for the property in the given object
     */
    function grabAndForget(instance, prop) {
        var grabbed = instance[prop];
        delete instance[prop];

        return grabbed;
    }

    // generate random text in given length (defaults to 32)
    function genRandomText(len) {
        len = len || 32;
        var pool = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678',
            generated = '';
        for (var i = 0, n = pool.length; i < len; i++) {
            generated += pool.charAt(Math.floor(Math.random() * n));
        }

        return generated;
    }

    /**
     * @param {Object} options.  refer to plupload.Uploader.
     *  - {Boolean} preserveFilename   when true the name of the uploaded file will be preserved, otherwise a random text
     *                                 will be used as the filename. Defaults to true.
     *  - {String} url                 (Required) uri to retrieve policy (and callback) for uploading.
     *  - {String} ossEndpoint         (Optional) endpoint to access to oss, typically in <schema>://<bucket>.<endpoint>
     *                                 form. Although it's optional, it'll get better performance if configured. e.g.
     *                                 http://post-test.oss-cn-hangzhou.aliyuncs.com
     *  - {Boolean} sharePolicy        when true, the created uploader will share a common policy, otherwise it will
     *                                 use its own policy. Defaults to false.
     *                                 CAUTION: Turn it on with caution - only when you have the same policy for all oss
     *                                 requests, you can turn it on, or you may get it mixed up.
     *
     *
     *  - THE REST PARAMS ARE THE SAME AS WHAT LAYUI UPLOAD COMPONENT REQUIRES *EXCEPT* OTHERWISE STATED.
     *    REFER http://www.layui.com/doc/modules/upload.html FOR THOSE OPTIONS.
     */
    function Uploader(options) {
        options = Object.assign({}, {
            preserveFilename: true,
        }, options);

        var preserveFilename = grabAndForget(options, 'preserveFilename'),
            uri = grabAndForget(options, 'url'),
            ossEndpoint = grabAndForget(options, 'ossEndpoint'),
            shared = grabAndForget(options, 'sharePolicy'),
            policy = shared ? layui.__policy__ : null;

        // enhanced version of requestPolicy(), which ensures that the policy will be valid(non-expired).
        function ensurePolicyValid() {
            var now = new Date().getTime() / 1000;  // time of seconds from Unix Epoch

            // if policy not grabbed or expired, grab one. For the expiring thing,
            // we add 3 seconds as a buffer for some kind of tolerance.
            if (!policy || policy.expire < now + 3) {
                policy = requestForPolicy(uri);
                shared && (layui.__policy__ = policy);
            }

            return policy;
        }

        // compute file's path(also serves as the key) that the file will be uploaded onto oss
        function computeFilepath(file) {
            // Refer https://www.alibabacloud.com/help/zh/doc-detail/31988.htm, 表单域 section for more.
            //
            // If filename is requested to be preserved (by setting preserveFilename option as true), the filename
            // will be kept after uploaded to oss. Otherwise, we'll use a randomly generated text as the filename.
            //
            return policy.dir + (preserveFilename ? '${filename}' : genRandomText());
        }

        // 'before' is fired when just before a file is uploaded. This event enables you to override settings
        // on the uploader instance before the file is uploaded.
        options.before = applyAdvice(function (obj) {
            // ensure that policy is valid (the data option below depends on this)
            ensurePolicyValid();
        }, undefined, options.before);

        options.data = {
            'key' : function () {
                return computeFilepath();  // file path as unique key
            },
            'policy': function () {
                return policy.policy;
             },
            'OSSAccessKeyId': function () {
                return policy.accessid;
            },
            'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
            'callback' : function () {
                return policy.callback;
            },
            'signature': function () {
                return policy.signature;
            }
        };

        // if ossEndpoint not configured, grab from remote server
        if (!ossEndpoint) {
            // force request so that we can get some config from remote server
            ensurePolicyValid();
            ossEndpoint = policy.host;
        }
        options.url = ossEndpoint;

        this.options = options;
    }


    Uploader.prototype = {
        render: function () {
            upload.render(this.options);
        }
    };

    // mimic layui's upload.render() interface
    ossupload.render = function (options) {
        new Uploader(options).render();
    };

    exports('ossupload', ossupload);
});