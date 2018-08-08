/**
 * Created by 墨盒 on 2017/3/8.
 * 相册管理插件
 */

function ImageManager(data) {

    //保存this
    var domThis = this;
    //相册的父元素
    var $image_group = null;

    var url = data.url;

    this.onFinish = function (dir) {
        if ($image_group !== null && typeof ($image_group) !== 'undefined') {
            var $dirs = $image_group.find('.item');
            for (var i = 0; i < $dirs.length; i++) {
                var dirName = $dirs.eq(i).find('.name:eq(0)').html();
                if (dirName === dir.name) {
                    var oldCount = parseInt($dirs.eq(i).find('.image:eq(0) .count:eq(0)').html());
                    $dirs.eq(i).find('.image:eq(0) .count:eq(0)').html((oldCount + dir.count));
                    $dirs.eq(i).find('.image:eq(0) img:eq(0)').attr('src', dir.cover);
                }
            }
        }
    };
    if (typeof data.onFinish === 'function') {
        var old = this.onFinish;
        this.onFinish = function (dir) {
            old(dir);
            data.onFinish(dir);
        }
    }


    //相册点击事件注册函数
    var bindDirClick = function ($div) {
        if ($div !== null && typeof($div) !== 'undefined')
            $div.on('click', function () {
                domThis.enter({
                    cover: $(this).find('.image:eq(0) img:eq(0)').attr('src'),
                    name: $(this).find('.name:eq(0)').html(),
                    count: $(this).find('.image:eq(0) .count:eq(0)').html()
                });
            });
    };
    //添加相册方法
    var addDir = function (dir) {
        if ($image_group !== null && typeof($image_group) !== 'undefined') {
            var $item = $('<div class="item"></div>');
            $item.append('<div class="image"><img src="' + dir.cover + '"><span class="count not_select">' + dir.count + '</span></div>');
            $item.append('<div class="name">' + dir.name + '</div>');
            $image_group.append($item);
            return $item;
        }
        return null;
    };

    //添加方法
    this.append = function (dir) {
        bindDirClick(addDir(dir));
    };

    //删除方法
    this.remove = function (index) {
        if ($image_group !== null && typeof ($image_group) !== 'undefined') {
            $image_group.find('.item:eq(' + index + ')').remove();
        }
    };

    this.getDirs = function () {
        var dirs = [];
        if ($image_group !== null && typeof ($image_group) !== 'undefined') {
            var $items = $image_group.find('.item');
            for (var i = 0; i < $items.length; i++) {
                dirs.push({
                    order: i,
                    cover: $items.eq(i).find('.image:eq(0) img:eq(0)').attr('src'),
                    name: $items.eq(i).find('.name:eq(0)').html(),
                    count: $items.eq(i).find('.image:eq(0) .count:eq(0)').html()
                });
            }
        }
        return dirs;
    };

    this.dirs = [
        // {
        //     cover: '',
        //     name: '',
        //     count: 0
        // }
    ];
    if (data.dirs instanceof Array) {
        this.dirs = data.dirs;
    }
    //创建相册回调
    this.createDir = function (dirName) {

    };

    if (typeof data.onCreateDir === 'function')
        this.createDir = data.onCreateDir;

    //删除回调
    this.onRemove = function (dirs) {

    };

    if (typeof data.onRemove === 'function')
        this.onRemove = data.onRemove;

    //点击相册回调
    this.enter = function (dir) {

    };
    if (typeof data.enter === 'function')
        this.enter = data.enter;


    //初始化相册
    if (typeof data.owner !== 'undefined') {
        var $owner = $(data.owner);
        //按钮组
        $owner.append('<div class="group"><a class="a_btn" href="javascript:void(0);">上传图片</a>' +
            '<a class="a_btn" href="javascript:void (0);">创建相册</a>' +
            '<a class="a_btn" href="javascript:void (0);">编辑</a></div>');
        //相册组
        var $images = $('<div class="image_group"></div>');
        $image_group = $images;
        for (var i = 0; i < this.dirs.length; i++) {
            // if (typeof this.dirs[i] === 'undefined')
            //     break;
            //添加相册 并 注册相册点击事件
            bindDirClick(addDir(this.dirs[i]));
        }
        $owner.append($images);
        //注册 上传 按钮事件
        $owner.find('.group:eq(0) a:eq(0)').on('click', function () {

            var dirs = domThis.getDirs();
            var options = [];
            for (var i = 0; i < dirs.length; i++) {
                options.push({
                    value: dirs[i].name,
                    text: dirs[i].name
                });
            }

            var upload = new ImageUpload({
                title: '图片上传',
                select: {
                    name: '选择相册',
                    options: options
                },
                url: url,
                prefix: data.prefix,
                onFinish: domThis.onFinish
            });
            upload.show();

        });
        //注册 创建相册 按钮事件
        $owner.find('.group:eq(0) a:eq(1)').on('click', function () {
            var dirName;
            dirName = window.prompt('相册名字');
            if (dirName !== '' && dirName !== null) {
                if (domThis.createDir(dirName)) {
                    domThis.append({
                        name: dirName,
                        cover: data.defaultCover,
                        count: 0
                    });
                }
            }
        });

        //注册 编辑 按钮事件
        $owner.find('.group:eq(0) a:eq(2)').on('click', function () {
            if ($owner.find('.image_group .item .image input[type=checkbox]').length > 0) {//处于编辑状态

                $owner.find('.group a:eq(3)').remove();//删除全选按钮
                $owner.find('.group label').remove();//删除全选的复选框

                $owner.find('.image_group .item .image .input_checkbox').remove();//删除图片上方的复选框
                //去掉原来的 事件
                $owner.find('.image_group .item').css('cursor', 'default').unbind('click');
                bindDirClick($owner.find('.image_group .item').css('cursor', 'pointer'));
            } else {//进入编辑状态
                //添加删除按钮
                $owner.find('.group')
                    .append($('<a class="a_btn" style="background: red" href="javascript:void(0);">删除</a>')
                        .on('click', function () {
                            var $checks = $owner.find('.image_group .item .image input[type=checkbox]');
                            var dirs = [];
                            for (var i = 0; i < $checks.length; i++) {
                                if ($checks.eq(i).is(':checked')) {
                                    // var $item = $checks.eq(i).parent().parent().parent();
                                    dirs.push({
                                        order: i,
                                        cover: $owner.find('.image_group .item .image img').eq(i).attr('src'),
                                        name: $owner.find('.image_group .item .name').eq(i).html(),
                                        count: $owner.find('.image_group .item .image .count').eq(i).html()
                                    });
                                    // $item.remove();
                                }
                            }
                            //回调
                            if (domThis.onRemove(dirs)) {
                                for (var i = 0; i < $checks.length; i++) {
                                    if ($checks.eq(i).is(':checked')) {
                                        var $item = $checks.eq(i).parent().parent().parent();
                                        $item.remove();
                                    }
                                }
                            }
                        }))
                    .append('<label style="margin-left: 30px"><input class="regular-checkbox" type="checkbox" id="allCheckbox"><label for="allCheckbox"></label>全选</label>')
                    .find('input[type=checkbox]').on('change', function () {//全选功能
                    $owner.find('.image_group .item .image .input_checkbox input[type=checkbox]').prop('checked', $(this).is(':checked'));
                });
                $owner.find('.image_group .item .image').append('<div class="input_checkbox" ><input class="regular-checkbox" type="checkbox" id="checkbox"><label for="checkbox"></label></div>');
                $owner.find('.image_group .item').css('cursor', 'pointer').unbind('click').on('click', function () {
                    var $checkbox = $(this).find('.image input[type=checkbox]').eq(0);
                    $checkbox.prop('checked', !$checkbox.is(':checked'));
                });
            }
        });


    }


}


