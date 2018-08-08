/**
 * Created by 墨盒 on 2017/3/7.
 * 图片上传插件
 */
function ImageUpload(data) {

    var domThis = this;
    this.title = "标题";
    this.select = {
        name: "",
        options: []
    };
    if (typeof data.title !== 'undefined')
        this.title = data.title;
    if (typeof data.select === 'object')
        this.select = data.select;
    this.zIndex = 100;
    if (typeof data.zIndex !== 'undefined')
        this.zIndex = data.zIndex;
    this.skin = 'upload_content';
    if (typeof  data.skin !== 'undefined')
        this.skin = data.skin;
    //图片上传的url
    this.url = data.url;

    var $contend = null;
    var $file = null;

    this.show = function () {
        $contend = $('<div id="' + this.skin + '"></div>');
        var $move_head = $('<div id="move_head"></div>');
        $move_head.append('<input type="button" value="关闭"><div class="title">' + this.title + '</div>');
        $move_head.find('input[type=button]').on('click', function () {
            $contend.remove();
        });
        var oldX = 0, oldY = 0, flag = false, mX, mY;
        $move_head.on('mousedown', function (e) {
            if (e.which === 1) {
                oldX = $contend.offset().left;
                oldY = $contend.offset().top;
                mX = e.pageX;
                mY = e.pageY;
                flag = true;
            }
        }).on('mouseup', function (e) {
            if (e.which === 1) {
                flag = false;
            }
        }).on('mousemove', function (e) {
            if (flag && e.which === 1) {
                $contend.css({
                    left: e.pageX - mX + oldX,
                    top: e.pageY - mY + oldY
                });
            }
        });
        $contend.append($move_head);

        var $main = $('<div class="main"><div class="manager_header"><span>选择上传相册：</span></div></div>');

        var $select = $('<select name="' + this.select.name + '"></select>');
        for (var i = 0; ; i++) {
            if (typeof this.select.options[i] === 'undefined')
                break;
            $select.append('<option value="' + this.select.options[i].value + '">' + this.select.options[i].text + '</option>');
        }
        $main.find('.manager_header:eq(0)').append($select);
        $main.find('.manager_header:eq(0)').append('<span><input type="file" value="选择图片" multiple="multiple" accept="image/png,image/jpeg,image/gif">上传过程中请不要操作原图片！<input type="button" value="上传"></span>');
        $main.append('<div class="quartz"></div>');
        $file = $main.find('.manager_header:eq(0)>span:eq(1)>input[type=file]');

        $main.find('.manager_header:eq(0) span:eq(1) input[type=button]:eq(0)').on('click', function () {
            $contend.dequeue('upload').dequeue('upload').dequeue('upload');
            $file.prop('disabled', true);
        });

        $contend.append($main);
        $('body').append($contend);
        $contend.css('zIndex', this.zIndex);
        var queue = function (file, path, progress, index, count) {
            var formData = new FormData();
            formData.append("file", file);
            formData.append("path", path);
            // formData.append("isOver", true);
            var upFunc = function () {
                $.ajax({
                    url: 'upload',
                    type: 'POST',
                    data: formData,
                    dataType: 'json',
                    processData: false,
                    contentType: false,
                    xhr: function () {
                        var xhr = $.ajaxSettings.xhr();
                        xhr.upload.addEventListener("progress", function (evt) {
                            if (evt.lengthComputable) {
                                progress.max = evt.total;
                                progress.value = evt.loaded;
                                if (evt.loaded === evt.total) {

                                }
                            }
                        }, false);
                        return xhr;
                    },
                    success: function (responseStr) {
                        if (responseStr.status === 0) {
                            $(progress).parent().remove();
                            //上传完成调用下一个方法
                            $contend.dequeue('upload');
                            if (index === (count - 1)) {
                                var prefix = typeof (data.prefix) === 'undefined' ? '' : data.prefix;
                                domThis.onFinish({
                                    name: path,
                                    count: count,
                                    cover: prefix + "/" + path + '/' + responseStr.img
                                });
                                $file.prop('disabled', false);
                            }
                        } else {
                            layer.open({
                                type: 0
                                , title: false
                                , icon: 2
                                , content: '上传失败，已中断后续上传'
                                , btn: '知道了'
                                , btnAlign: 'c' //按钮居中
                                , shade: 0 //不显示遮罩
                                , yes: function () {
                                    layer.closeAll();
                                    $file.prop('disabled', false);
                                }
                            });
                        }
                    },
                    error: function () {
                        progress.value = 0;
                    }
                });
            };
            $contend.queue('upload', upFunc);
        };
        //文件选择事件
        $contend.find('.main:eq(0) input[type=file]:eq(0)').on("change", function () {
            for (var i = 0; i < this.files.length; i++) {
                if (this.files[i].type.indexOf('image') > -1) {
                    var $div = $('<div class="pic"></div>');
                    $div.append($('<img>').attr('src', window.URL.createObjectURL(this.files[i])));
                    $div.append('<progress max="100" value="0"></progress>');
                    $contend.find('.main:eq(0) .quartz:eq(0)').append($div);
                    queue(this.files[i], $('select').eq(0).val(), $div.find('progress').get(0), i, this.files.length);
                }
            }

        });
    };

    this.onFinish = function (dir) {
    };
    if (typeof data.onFinish === 'function')
        this.onFinish = data.onFinish;

    this.clear = function () {
        $contend.html();

    };

}