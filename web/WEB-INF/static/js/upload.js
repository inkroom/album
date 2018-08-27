/**
 * Created by 墨盒 on 2017/8/30.
 * 新上传js
 */
function loadUpload(url, upload) {
    var uploader;

    var count = {
        success: 0,
        fail: 0,
        exist: 0
    };
    window.count = count;

    ajax({
        url: url,
        dataType: 'json',
        type: 'post',
        success: function (result) {
            var options = '';
            for (var i = 0; i < result.data.albums.length; i++) {
                options += ('<option value="' + result.data.albums[i].id + '">' + result.data.albums[i].name + '</option>');
            }
            layer.open({
                type: 1,
                shade: false,
                resize: false,
                skin: 'layer-upload',
                area: ['60%', '70%'],
                title: '上传图片', //不显示标题
                content: '<div class="upload"><div class="upload-head"><span style="float: left"><span>选择相册：</span>' +
                '<select id="album">' + options + '</select>' +
                '<input type="file" style="display: none"/>' +
                '<div class="btn" style="position: relative">选择图片</div></span>' +
                '<span style="float: right;margin-right: 10%">上传过程中请不要操作原始图片' +
                '<button class="btn">开始上传</button></span></div><div class="upload-content"></div></div>',
                cancel: function () {
                    if (uploader !== null) {
                        uploader.destroy();
                        delete uploader;
                        uploader = null;
                    }
                }
            });
            console.log($('.upload-head .btn:eq(0)').length);
            uploader = WebUploader.create({
                // 选完文件后，是否自动上传。
                auto: false,
                // swf文件路径
                swf: 'http://static.inkroom.cn/image/lib/webuploader/0.1.5/Uploader.swf',

                // 文件接收服务端。
                server: upload,

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '.upload-head .btn:eq(0)',

                // 只允许选择图片文件。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });
            //  $('.upload-head .btn:eq(0)').on('click', function () {
            //      // $(this).find('label').trigger('click');
            //      console.log(1231);
            // });
            $('.upload-head .btn:eq(1)').on('click', function () {
//            $('.upload-head input[type=file]').trigger('click');
                if (uploader.isInProgress()) {
                    show('正在上传中');
                    return;
                } else if (uploader.getFiles().length === 0) {
                    return;
                }
                uploader.upload();
            });

            // 当有文件添加进来的时候
            uploader.on('fileQueued', function (file) {
                var $div = $('<div class="upload-item" id="' + file.id + '">' +
                    '<div class="upload-image">' +
                    '<span class="layui-layer-setwin"><span class="layui-layer-ico layui-layer-close layui-layer-close2"></span></span> ' +
                    '<img src="" alt=""><span class="upload-text">' +
                    '</span></div><progress max="100" value="0"></progress></div>');
                // 创建缩略图
                // 如果为非图片文件，可以不用调用此方法。
                // thumbnailWidth x thumbnailHeight 为 100 x 100
                uploader.makeThumb(file, function (error, src) {
                    var $img = $div.find('img');
                    if (error) {
                        $img.attr('alt', '不能预览');
                        return;
                    }
                    $img.attr('src', src);
                }, 200, 150);
                $div.find('.layui-layer-close').on('click', function () {
                    uploader.removeFile(file, true);
                    $div.remove();
                });
                $div.find('.upload-text').html(file.name);
                $('.upload-content').append($div);
                var $btn = $('.upload-head .btn:eq(0)').parent();
                $btn.find('#count').remove();
                $btn.append('<span id="count">已选择' + uploader.getFiles().length + '个文件</span>')

            });
// 文件上传失败，显示上传出错。
            uploader.on('uploadError', function (file) {
                var $item = $('#' + file.id).find('.upload-image');
                if ($item.find('.upload-text-error').length === 0) {
                    $item.append('<span class="upload-text-error">上传失败</span>')
                }
//                $error = $li.find('div.error');
//
//            // 避免重复创建
//            if ( !$error.length ) {
//                $error = $('<div class="error"></div>').appendTo( $li );
//            }
//
//            $error.text('上传失败');
            });
            uploader.on('uploadProgress', function (file, percentage) {
//            console.log(percentage);
                var $item = $('#' + file.id).find('progress');
                $item.attr('value', percentage * 100);
                // 避免重复创建
//            if ( !$percent.length ) {
//                $percent = $('<div class="progress progress-striped active">' +
//                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
//                    '</div>' +
//                    '</div>').appendTo( $li ).find('.progress-bar');
//            }
//
//            $li.find('p.state').text('上传中');
//
//            $percent.css( 'width', percentage * 100 + '%' );
            });
            uploader.on('uploadSuccess', function (file, response) {
                // console.info(response);
                if (response.status === 0) {

                    count.success = count.success + 1;
                    var $item = $('#' + file.id).find('.upload-image');
                    if ($item.find('.upload-text-error').length === 0) {
                        $item.append('<span class="upload-text-error">上传成功</span>')
                    }
                    $item.parent().remove();
                } else if (response.status === 2) {//文件重复上传
                    var $item = $('#' + file.id).find('.upload-image');
                    if ($item.find('.upload-text-error').length === 0) {
                        $item.append('<span class="upload-text-error">上传重复</span>')
                    }
                    $item.parent().remove();
                    count.exist = count.exist + 1;
                    file.setStatus(WebUploader.File.Status.ERROR)
                } else {
                    var $item = $('#' + file.id).find('.upload-image');
                    if ($item.find('.upload-text-error').length === 0) {
                        $item.append('<span class="upload-text-error">上传失败</span>')
                    }
                    count.fail = count.fail + 1;
                    $item.parent().remove();
                    file.setStatus(WebUploader.File.Status.ERROR)
                }
            });
            uploader.on('startUpload', function () {
                $('.upload-head').find('input,button,.btn').prop('disabled', true).addClass('btn-disabled');
            });
            uploader.on('uploadFinished', function () {
                var stats = uploader.getStats();
                show('上传成功' + count.success + '张图片，' + count.fail + '张图片上传失败' + "，" + count.exist + "张图片重复上传")
                $('.upload-head').find('input,button,.btn').prop('disabled', false).removeClass('btn-disabled');
                var files = uploader.getFiles();
                for (var i = 0; i < files.length; i++) {
                    uploader.removeFile(files[i], true);
                }

                count.success = 0
                count.fail = 0;
                count.exist = 0;

                stats.successNum = 0;
                stats.progressNum = 0;
                stats.cancelNum = 0;
                stats.invalidNum = 0;
                stats.uploadFailNum = 0;
                stats.interruptNum = 0;
            });
            uploader.on('uploadBeforeSend', function (obj, data, headers) {
                data.album = $('#album').val();
                console.log("路径= " + location.href.toString());
                // data.owner = getId(location.href.search(), 0);
                // data.owner = location.href.search(/[1-9]*[0-9]+\/$/);
                data.owner = location.pathname.toString().match(/album\/([1-9]*[0-9]+)\//)[1];
                // console.log(location.href.sub());
                // console.log(location.href.small())
                // console.log(location.href.big());
                // console.log(location.host.toString());
                // console.log(location.pathname.toString());
                headers['X-Requested-With'] = 'XMLHttpRequest';
                console.log(data);
            });

            // var $file = $('<input type="file" />').on('change', function () {
            //     var file = new  WebUploader.File(this.files[0]);
            //     console.log(file);
            // });
            // $('body').append($file);
            // $file.trigger('click');
        }
    });

}
