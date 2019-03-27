/**
 * Created by 墨盒 on 2017/8/16.
 * 基本的js方法
 */
function show(content, btn, yes) {
    layer.open({
        type: 0
        , title: false
        , icon: false
        , shade: 0.3
        , content: content
        , btn: btn === null ? '知道了' : btn
        , btnAlign: 'c' //按钮居中
        , yes: function () {
            layer.close(layer.index);
            if (yes !== null && typeof yes === 'function')
                yes();
        }, cancel: function () {
            layer.close(layer.index);
            if (yes !== null && typeof yes === 'function')
                yes();
        }
    });
}

function layerConfirm(content, callback) {
     layer.confirm(content, {btn: ['确定', '取消']}, function () {
        layer.close(layer.index);
        callback();
    }, function () {

    });
}

function ajaxHandler(result, func) {
    for (var i = 0; i < func.length; i++)
        if (func[i].key === result.status) {
            func[i].callback();
            return;
        }
    switch (result.status) {
        case 0://成功
            break;
        case 1://失败
            break;
        case 2://文件已存在
            break;
        case 3://相册不存在
            break;
        case 5://参数格式不正确
            show(result.message, '知道了', null);
            break;
        case 6://登陆失效或未登录
            show('登陆已失效或未登录', '重新登陆', function () {
                location.href = '/login'
            });
            break;
        case 7://没有权限
            show('权限不足', null, null);
            break;
        case 8://登陆失败，账号密码错误
            break;
        case 9://已经异地登陆
            show('您的账号在异地登陆，您已掉线', '重新登陆', function () {
                location.href = '/login';
            });
            break;
        case 10://文件不存在
            show('图片不存在', null, null);
            break;
        case 11://发生异常
            show('服务器异常，请联系管理人员', null, null);
            break;
    }
}

function loadLazy(element) {
    //首先获取loading图片资源
    // $('body').append('<img id="loading" src="http://static.inkroom.cn/image/img/loading.jpg" style="display: none;">');
    // $('body #loading').remove();
    var lazy = function () {
        var top = $(window).scrollTop();
        var winH = $(window).height();
        var images = $(element);
        for (var i = 0; i < images.length; i++) {
            if (images.eq(i).attr('lazy') !== images.eq(i).attr('src')) {//未显示过
                var _top = images.eq(i).offset().top;
//                            console.log('top=' + top + '  _top=' + _top + " winH=" + winH + ' lazy=' + quartz.eq(i).attr('lazy'));
                if (_top > top && _top <= top + winH + 100)
                    images.eq(i).attr('src', images.eq(i).attr('lazy'));
            }
        }
    };
    $(window).on('scroll resize', function () {
        lazy();
    }).trigger('resize');
}

function getId(value, index) {
    // console.log(value.match(/\/[^\/]+\/([1-9]*[0-9]+)\/([1-9]*[0-9]+)*\/([1-9]*[0-9]+)*/));
    var albumId = value.match(/\/[^\/]+\/([1-9]*[0-9]+)\/([1-9]*[0-9]+)*\/([1-9]*[0-9]+)*/);
    if (albumId !== null && albumId.length > index + 1) {
        albumId = albumId[index + 1];
    } else {
        // show('错误数据', '刷新页面', function () {
        //     location.reload();
        // });
        return -1;
    }
    if (albumId === null || typeof (albumId) === 'undefined') {
        // show('错误数据', '刷新页面', function () {
        //     location.reload();
        // });
        return -1;
    }
    return parseInt(albumId);
}

//提前处理ajax返回的数据
function ajax(data) {
    var tempSuccess = data.success;
    data.dataType = 'json';
    // console.log(tempSuccess);
    data.success = function (result) {
        // var index = layer.getFrameIndex(window.name);
        layer.close(index);
        // layer.closeAll();
        switch (result.status) {
            case 5://参数格式不正确
                show(result.message, '知道了', null);
                return;
            case 6://登陆失效或未登录
                show('登陆已失效或未登录', '重新登陆', function () {
                    location.href = '/login'
                });
                return;
            case 7://没有权限
                show('权限不足', null, null);
                return;
            case 9://已经异地登陆
                show('您的账号在异地登陆，您已掉线', '重新登陆', function () {
                    location.href = '/login';
                });
                return;
            case 10://文件不存在
                show('图片不存在', null, null);
                return;
            case 11://发生异常
                show(result.message, null, null);
                return;
        }
        if (typeof tempSuccess === 'function')
            tempSuccess(result);
    };
    var index = -1;
    var tempError = data.error;
    data.error = function () {
        layer.close(layer.index);
        // layer.closeAll();
        show('网络错误！');
        if (typeof tempError === 'function')
            tempError();
    };
    var tempComplete = data.complete;
    data.beforeSend = function () {
        if(layer.v==='2.0') {//移动版layer
            layer.open({type:2,
                shade: false || data.shade //0.1透明度的白色背景
            })
        }else {
              index = layer.load(0, {
                  shade: false || data.shade //0.1透明度的白色背景
            });
        }

    };
    data.complete = function () {
        // layer.index = ; //0代表加载的风格，支持0-2
        // index = layer.load(0, {
        //     shade: false //0.1透明度的白色背景
        // });
    //    layer.close(layer.index);
        if (typeof tempComplete === 'function')
            tempComplete();
    };
    $.ajax(data);
}
