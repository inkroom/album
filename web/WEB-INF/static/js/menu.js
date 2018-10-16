/**
 * Created by 墨盒 on 2017/8/16.
 * 上下文菜单js
 */

function loadMenu(data) {
    $(data.owner).on('mouseover', function () {
        var $this = $(this);
        if ($this.find('.plus').length > 0) {

        } else {
            $this.css('position', 'relative')
                .append($('<img src="http://static.inkroom.cn/image/img/plus.png" class="plus"/>').on('click', function () {
                    var $popMenu = $('<div class="popMenu" style="right:5px;top:37px;"></div>"').on('click', function () {
                        return false;
                    });
                    for (var i = 0; i < data.menu.length; i++) {
                        var $menu = $('<div class="menu"></div>');
                        $menu.html(data.menu[i].value).attr('index', i);
                        $menu.on('click', function (e) {
                            data.menu[$(this).attr('index')].callback($(this));
                            e.stopPropagation();
                            return false;
                        });
                        $popMenu.append($menu);
                    }
                    $popMenu.css({
                        right: 5,
                        top: $(this).position().top + $(this).height()
                    }).on('mouseout', function (e) {
                        if (!(isInside($(this), e))) {
                            $(this).parent().find('.plus').remove();
                            $(this).remove();
                        }
                    });
                    $this.append($popMenu);
                    return false;
                }).on('mouseout', function (e) {
                    if (!(isInside($(this), e))) {
                        $(this).find('.plus').remove();
                        $(this).find('.popMenu').remove();
                    }
                }));
        }
    }).on('mouseout', function (e) {
       var $this = $(this).find('.popMenu');
        if($this.length==0){
			$this = $(this);
		}
        if (!(isInside($this, e))) {

            $(this).find('.plus').remove();
            $(this).find('.popMenu').remove();
        }
    });
}
function isInside($this, e) {
//                console.log($this);
    return e.pageX >= $this.offset().left && e.pageX < $this.offset().left + $this.width() && e.pageY >= $this.offset().top && e.pageY <= $this.offset().top + $this.height();
}