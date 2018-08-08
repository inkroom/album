/**
 * Created by 墨盒 on 2017/8/30.
 * 新上传js
 */
function loadUpload(url, upload, owner) {
    var uploader = null;
    var sources = [];

    function Img(im) {
        var ImgCls = {
            Obj: null,
            //按给定的宽和高进行智能缩小
            Resize: function (nWidth, nHeight) {
                var w, h, p1, p2; //计算宽和高的比例
                p1 = nWidth / nHeight;
                p2 = ImgCls.Obj.width / ImgCls.Obj.height;
                w = 0;
                h = 0;
                if (p1 > p2) { //按宽度来计算新图片的宽和高
                    w = nWidth;
                    h = nWidth * ( 1 / p2 );
                } else { //按高度来计算新图片的宽和高
                    h = nHeight;
                    w = nHeight * p2;
                }
                ImgCls.Obj.width = w;
                ImgCls.Obj.height = h;
            }, //按给定的宽和高进行固定缩小(会出现图片变形情况) //http://bizhi.knowsky.com
            ResizedByWH: function (nWidth, nHeight) {
                ImgCls.Obj.width = nWidth;
                ImgCls.Obj.height = nHeight;
            }, //按给定的宽进行等比例缩小
            ResizedByWidth: function (nWidth) {
                var p = nWidth / ImgCls.Obj.width;
                ImgCls.Obj.width = nWidth;
                ImgCls.Obj.height = parseInt(ImgCls.Obj.height * p);
            }, //按给定的高进行等比例缩小
            ResizedByHeight: function (nHeight) {
                var p = nHeight / ImgCls.Obj.height;
                ImgCls.Obj.height = nHeight;
                ImgCls.Obj.width = parseInt(ImgCls.Obj.width * p);
            }, //宽和高按百分比缩小
            ResizedByPer: function (nWidthPer, nHeightPer) {
                ImgCls.Obj.width = parseInt(ImgCls.Obj.width * nWidthPer / 100);
                ImgCls.Obj.height = parseInt(ImgCls.Obj.height * nHeightPer / 100);
            }
        };
        ImgCls.Obj = ( im && typeof im === 'object' ) ? im : document.getElementById(im);
        return ImgCls;
    }

    function count() {
        var $btn = $('.upload-head .btn:eq(0)').parent();
        $btn.find('#count').remove();
        $btn.append('<span id="count">已选择' + uploader.getFiles().length + '个文件</span>')
        // uploader.reset();
    }


    // 该文章《js实现缩略图功能》来源于图老师，网址：http://www.tulaoshi.com/n/20160219/1607616.html
    var createUploader = function (owner, album) {
        var u = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: false,
            // swf文件路径
            swf: 'http://static.inkroom.cn/image/lib/webuploader/0.1.5/Uploader.swf',

            // 文件接收服务端。
            server: 'common/' + owner + "/" + album + '/upload',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            // pick: '.upload-head .btn:eq(0)',

            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        // 当有文件添加进来的时候
        u.on('fileQueued', function (file) {
            if ($('#' + file.id).length === 0) {
                var $div = $('<div class="upload-item" id="' + file.id + '">' +
                    '<div class="upload-image">' +
                    '<span class="layui-layer-setwin"><span class="layui-layer-ico layui-layer-close layui-layer-close2"></span></span> ' +
                    '<img src="" alt=""><span class="upload-text">' +
                    '</span></div><progress max="100" value="0"></progress></div>');
                // 创建缩略图
                // 如果为非图片文件，可以不用调用此方法。
                // thumbnailWidth x thumbnailHeight 为 100 x 100
                // console.log(file);
                $div.find('img').attr('src', window.URL.createObjectURL(file.source));
                Img($div.find('img').get(0)).Resize(200, 150);
                // u.makeThumb(file, function (error, src) {
                //     console.log('error = ' + error);
                //     var $img = $div.find('img');
                //     if (error) {
                //         $img.attr('alt', '不能预览');
                //         return;
                //     }
                //     $img.attr('src', src);
                // }, 200, 150);
                $div.find('.layui-layer-close').on('click', function () {
                    u.removeFile(file.id, true);
                    $div.remove();
                    count();
                });
                $div.find('.upload-text').html(file.name);
                $('.upload-content').append($div);
                count();
            }
        });
// 文件上传失败，显示上传出错。
        u.on('uploadError', function (file) {
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
        u.on('uploadProgress', function (file, percentage) {
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
        u.on('uploadSuccess', function (file, response) {
            console.info(response);
            if (response.status === 0) {
                var $item = $('#' + file.id).find('.upload-image');
                if ($item.find('.upload-text-error').length === 0) {
                    $item.append('<span class="upload-text-error">上传成功</span>')
                }
                $item.parent().remove();
                u.removeFile(file, true);
                count();
            }
        });
        u.on('startUpload', function () {
            $('.upload-head').find('input,button').prop('disabled', true).addClass('btn-disabled');
        });
        u.on('uploadFinished', function () {
            console.log(uploader.getStats());
            show('上传成功' + uploader.getStats().successNum + '张图片，' + uploader.getStats().uploadFailNum + '张图片上传失败')
            $('.upload-head').find('input,button').prop('disabled', false).removeClass('btn-disabled');
        });
        u.on('uploadBeforeSend', function (obj, data, headers) {
            console.log(data);
            data.album = $('#album').val();
            data.owner = getId(location.href.toString(), 0);
            headers['X-Requested-With'] = 'XMLHttpRequest';
            console.log(data);
        });

        return u;
    };
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
                '<input type="file" multiple style="display: none"/>' +
                '<div class="btn" style="position: relative">选择图片</div></span>' +
                '<span style="float: right;margin-right: 10%">上传过程中请不要操作原始图片' +
                '<button class="btn">开始上传</button></span></div><div class="upload-content"></div></div>'
            });
            console.log($('.upload-head .btn:eq(0)').length);
            var $file = $('.upload-head input[type=file]');
            $('.upload-head .btn:eq(0)').on('click', function () {//选择图片按钮
                $file.trigger('click');
            });
            $file.on('change', function () {
                if (uploader === null)
                    uploader = createUploader(owner, $('#album').val());
                for (var i = 0; i < this.files.length; i++) {
                    sources.push(new WebUploader.File(this.files[i]));
                    // uploader.addFiles(new WebUploader.File(this.files[i]));
                }
            });
            $('#album').on('change', function () {
                if (uploader !== null) {
                    uploader.option('server', $(this).val());
                    // var files = uploader.getFiles();
                    // uploader.destroy();
                    // delete uploader;
                    // // console.log(files);
                    // uploader = createUploader(owner, $(this).val());
                    // // for (var i = 0; i < files.length; i++) {
                    // uploader.addFiles((files));
                    // }
                }
            });

            //  $('.upload-head .btn:eq(0)').on('click', function () {
            //      // $(this).find('label').trigger('click');
            //      console.log(1231);
            // });
            $('.upload-head .btn:eq(1)').on('click', function () {
//            $('.upload-head input[type=file]').trigger('click');
                uploader = createUploader(owner, $('#album').val());
                uploader.addFiles(sources);
                uploader.upload();
//                 if (uploader !== null) {
//                     if (uploader.isInProgress()) {
//                         show('正在上传中');
//                         return;
//                     } else if (uploader.getFiles().length === 0) {
//                         return;
//                     }
//                     uploader.upload();
//                 }
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
