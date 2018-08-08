<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>上传图片</title>
    <!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/style.css">
    <style type="text/css">
        .content {
            background-color: #ade0dd;
            width: 80%;
            margin: 0 auto;
        }

        .images .pic {
            display: inline-block;
            margin: 15px;
            width: 200px;
            /*height: 200px;*/
            vertical-align: middle;
            text-align: center;
        }

        .images .pic img {
            width: inherit;
            vertical-align: middle;
            border: black medium inset;
            line-height: 200px;
            /*max-height: 200px;*/
            /*max-width: 200px;*/
        }

        .images .pic progress {
            width: 75%;
            margin: 5px 0;
            display: inline-block;
        }

        .images .pic span {
            margin-left: 2%;
        }


    </style>
    <singleLink type="text/css" rel="stylesheet" href="http://static.inkroom.cn/image/css/upload.css">

</head>
<body>
<div class="wrap">
    <div class="wrapper">
    </div>
</div>
<div class="footer">@墨盒</div>
<div id="upload" style="display: none">
    <%--<div class="upload">--%>
    <%--<div class="upload-head">--%>
    <%--<span style="float: left">--%>
    <%--<span>选择相册：</span>--%>
    <%--<select id="album">--%>
    <%--<option value="k">K</option>--%>
    <%--<option value="神的记事本">神的记事本</option>--%>
    <%--</select>--%>
    <%--<input type="file" style="display: none"/>--%>
    <%--<div class="btn">选择图片</div>--%>
    <%--</span>--%>
    <%--<span style="float: right;margin-right: 10%">上传过程中请不要操作原始图片<button class="btn">开始上传</button></span>--%>
    <%--</div>--%>
    <%--<div class="upload-content">--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text-error">上传失败</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="0"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="20"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="20"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="20"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="20"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="20"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="upload-image">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<img src="http://static.inkroom.cn/image/img/1.jpg" alt="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<span class="upload-text">测试文本.jpg</span>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<progress max="100" value="20"></progress>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--</div>--%>
    <%--</div>--%>

</div>

<script type="text/javascript" src="http://static.inkroom.cn/image/js/jquery.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/index.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/upload.js"></script>
<script type="text/javascript">
    $(function () {
        loadUpload('getAlbums');

//        function queue(file, path, progress, number, isFinish) {
//            var formData = new FormData();
//            formData.append("file", file);
//            formData.append("path", path);
//            formData.append("isOver", true);
//            var upFunc = function () {
//                $.ajax({
//                    url: 'upload',
//                    type: 'POST',
//                    data: formData,
//                    dataType: 'json',
//// 告诉jQuery不要去处理发送的数据
//                    processData: false,
////// 告诉jQuery不要去设置Content-Type请求头
//                    contentType: false,
//                    beforeSend: function () {
//                        console.log("正在进行，请稍候");
//                    },
//                    complete: function () {
//                        console.log("请求已发出");
//                    },
//                    xhr: function () {
//                        var xhr = $.ajaxSettings.xhr();
//                        xhr.upload.addEventListener("progress", function (evt) {
////                                    var progressBar = document.getElementById("progressBar");
////                                    var percentageDiv = document.getElementById("percentage");
//                            console.log(evt);
//                            if (evt.lengthComputable) {
//                                progress.max = evt.total;
//                                progress.value = evt.loaded;
//                                number.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
//                                if (evt.loaded == evt.total) {
//                                    console.log("上传完成100%");
//                                }
//                            }
//                        }, false);
//                        return xhr;
//                    },
//                    success: function (responseStr) {
//                        if (responseStr.status == 0) {
//                            console.log(responseStr);
//                            //上传完成调用下一个方法
//                            $('.quartz').dequeue('upload');
//                            if (isFinish)
//                                alert("全部执行完毕");
//                        } else {
//                            console.log("失败");
//                        }
//                    },
//                    error: function () {
//                        console.log("error");
//                    }
//                });
//            };
//            //将方法添加到队列
//            $('.quartz').queue('upload', upFunc);
//        }

//        $('input[type=button]').on('click', function () {
//            //同时上传三个图片
//            $('.quartz').dequeue('upload').dequeue('upload').dequeue('upload');
//            console.log("after");
//        });
//
//        $('input[type=file]').on('change', function () {
//            if (this.files.length > 0)
//                $('.quartz').html('');
//            for (var i = 0; i < this.files.length; i++) {
//                if (this.files[i].type.indexOf('image') > -1) {
////                            $('body').append($('<img>').attr('src', window.URL.createObjectURL(this.files[i])));
//                    var $div = $('<div class="pic"></div>');
//                    $div.append($('<img>').attr('src', window.URL.createObjectURL(this.files[i])));
//                    $div.append('<progress max="100" value="0"></progress>');
//                    $div.append('<span>0%</span>');
//                    $('.quartz').append($div);
//                    //上传方法入队
//                    queue(this.files[i], $('select').eq(0).val(), $div.find('progress').get(0), $div.find('span').get(0), (i == (this.files.length - 1)) ? true : false);
//                }
//            }
//        }).trigger('change');

//                $('input[type=file]').trigger('change');
    });
</script>
</body>
</html>