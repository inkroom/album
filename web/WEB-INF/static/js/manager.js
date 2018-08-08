/**
 * Created by 墨盒 on 2017/8/19.
 * 管理js
 */
function loadManger(data) {

    var $managerGroup = $('<div class="manager_group"></div>');

    var $btnUpload = $('<a href="javascript:void(0);" class="a_btn">上传图片</a>').on('click', data.upload);
    var $btnCreate = $('<a href="javascript:void(0);" class="a_btn">创建相册</a>').on('click', data.create);
    var $btnManager = $('<a href="javascript:void(0);" class="a_btn">批量管理</a>').on('click', function () {
        var $images = $(data.images);
        var $btnDelete = null;
        if ($images.find('.input_checkbox').length > 0) {//处于编辑状态
            $images.find('.input_checkbox').remove();
            $managerGroup.find('.a_btn:eq(3)').remove();
            $managerGroup.find('label').remove();
            //找回click事件
            for (var i = 0; i < $images.length; i++) {
                // $images.eq(i).parent().prop('disabled',true);
                $images.eq(i).parent().unbind('click').attr('onclick', $images.eq(i).parent().attr('temp')).attr('temp', null);
            }
            if (typeof data.cancel === 'function')
                data.cancel();
        } else {
            for (var i = 0; i < $images.length; i++) {
                // $images.eq(i).parent().prop('disabled',true);
                $images.eq(i).css('position', 'relative').unbind('click');
                $images.eq(i).parent().unbind('click').attr('temp', $images.eq(i).parent().attr('onclick')).attr('onclick', null);
                $images.eq(i).prepend($('<div class="input_checkbox"><input class="regular-checkbox" id="checkbox" type="checkbox"><label for="checkbox"></label></div>'));
                $images.eq(i).on('click', function () {
                    var $check = $(this).find('input[type=checkbox]');
                    $check.prop('checked', !$check.prop('checked'));
                })
            }

            $btnDelete = $('<a href="javascript:void(0);" class="a_btn" style="background: red">删除</a>').on('click', function () {
                var images = [];
                var $inputs = $images.find('input[type=checkbox]');

                for (var i = 0; i < $inputs.length; i++) {
                    if ($inputs.eq(i).prop('checked') || $inputs.eq(i).is(':checked')) {
                        images.push($images.eq(i));
                    }
                }
                if (images.length > 0)
                    data.remove(images);
            });
            $managerGroup.append($btnDelete);
            $managerGroup.append($('<label style="margin-left: 30px"><input class="regular-checkbox" id="allCheckbox" type="checkbox">' +
                '<label for="allCheckbox"></label>全选</label>'));
            $managerGroup.find('input[type=checkbox]').on('change', function () {
                // alert($images.find('input[type=checkbox]').length);
                // var $inputs = $images.find('input[type=checkbox]');
                // for (var i = 0; i < $inputs.length; i++)
                //     $inputs.eq(i).prop('checked', $(this).prop('checked'));
                $images.find('input[type=checkbox]').prop('checked', $(this).prop('checked'));
                // for (var i = 0; i < $images.length; i++) {
                //     $images.eq(i).find('input[type=checkbox]').prop('checked', $(this).prop('checkbox'));
                // }
            });
            // for(var i=0;i<$images.length;i++){
            // }
        }
    });

    $managerGroup.append($btnUpload);
    $managerGroup.append($btnCreate);
    $managerGroup.append($btnManager);

    $(data.id).before($managerGroup);
}
