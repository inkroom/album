/**
 * Created by 墨盒 on 2017/8/16.
 * 相册js
 */
$(function () {
    loadLazy('.image_item >.image> img');
});

function clearCreate($obj) {
    $obj.find('input[type=text]').val('');
    $obj.find('textarea').val('');
    $obj.find('input[type=button]').removeAttr('disabled');
    $obj.find('.count').html('0');

}

function loadAlbumMenu(path) {
    loadMenu({
        path:path,
        owner: '.image_item',
        menu: [
            {
                value: '编辑',
                callback: function ($menu) {
                    ajax({
                        url: getId($menu.parent().parent().find('.image>img').attr('lazy'), 1) + '/',
                        type: 'post',
                        success: function (result) {
                            if (result.status === 0) {
                                console.log(1);
                                loadCreate({id: result.data.album.id});
                                console.log(2);
                                $('#createTable input[name=name]').val(result.data.album.name).trigger('keyup');
                                $('#createTable textarea[name=content]').val(result.data.album.content).trigger('keyup');
                                $('#createTable select[name=type]').val(result.data.album.type).trigger('change');
                                console.log(3);
                                if (result.data.album.authority < 2) {
                                    $('#createTable select[name=authority]').val(result.data.album.authority).trigger('change');
                                    layer.open({
                                        type: 1,
                                        shade: 0.7,
                                        title: '修改相册', //不显示标题
                                        content: $('#createTable') //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                                    });
                                }
                                else {
                                    $('#createTable select[name=authority]').val('2').trigger('change');
                                    console.log(4);
                                    ajax({
                                        url: getId($menu.parent().parent().find('.image>img').attr('lazy'), 1) + '/' + result.data.album.authority + "/getQuestion",
                                        success: function (r) {
                                            $('#createTable input[name=question]').val(r.data.question).trigger('keyup');
                                            layer.open({
                                                type: 1,
                                                shade: 0.7,
                                                title: '修改相册', //不显示标题
                                                content: $('#createTable') //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                                            });
                                        }
                                    })
                                }
                            }
                        }
                    });


                }
            },
            {
                value: '删除',
                callback: function ($menu) {
                    // console.log('删除');
                    layer.confirm('您确定要删除该相册？', {btn: ['确定', '取消']}, function () {
                        var albumId = $menu.parent().parent().find('.image>img').attr('lazy').match(/^\/common\/[1-9]*[0-9]\/([1-9]*[0-9])/)[1];
                        if (albumId === null || typeof (albumId) === 'undefined') {
                            show('错误数据', '刷新页面', function () {
                                location.reload();
                            });
                            return;
                        }
                        ajax({
                            url: albumId + '/removeAlbum',
                            type: 'get',
                            dataType: 'json',
                            success: function (result) {
                                if (result.status === 0) {
                                    $menu.parent().parent().remove();
                                    show('删除相册成功')
                                } else if (result.status === 1) {
                                    show('删除相册失败', null, null)
                                } else if (result.status === 3) {
                                    show('该相册不存在', null, null)
                                }
                                // ajaxHandler(result, [
                                //     {
                                //         key: 0,
                                //         callback: function () {
                                //             $menu.parent().parent().remove();
                                //             show('删除相册成功')
                                //         }
                                //     },
                                //     {
                                //         key: 1,
                                //         callback: function () {
                                //             show('删除相册失败', null, null)
                                //         }
                                //     },
                                //     {
                                //         key: 3,//相册不存在
                                //         callback: function () {
                                //             show('该相册不存在', null, null)
                                //         }
                                //     }
                                // ])
                            },
                            error: function () {
                                show('网络错误', null, null);
                            }
                        });
                    }, function () {

                    });

                }
            }
        ]
    });
}
function loadAlbumManager(uploadUrl) {
    loadManger({
        id: '.image_group'
        , images: '.image'
        , upload: function () {
            //页面层
            loadUpload('', uploadUrl);
        }, create: function () {
            clearCreate($('#createTable'));
            layer.open({
                type: 1,
                shade: false,
                title: '创建相册', //不显示标题
                content: $('#createTable') //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            });
            // layer.open({
            //     type: 1,
            //     skin: 'layui-layer-rim', //加上边框
            //     // area: ['420px', '240px'], //宽高
            //     content: '<table id="form"><tr><td class="col-left">相册名称：</td><td class="col-right"><input type="text" maxlength="30"><span><span class="count">0</span>/30</span></td> </tr> <tr><td class="col-left">相册描述：</td><td class="col-right"><textarea maxlength="200"></textarea><span><span class="count">0</span>/200</span></td></tr><tr><td class="col-left">分类：</td><td class="col-right"><select name="" id=""><option value="">类别1</option><option value="">类别2</option></select></td></tr><tr><td class="col-left">权限：</td><td class="col-right"><select name="" id=""><option value="">所有人可见</option><option value="">仅自己可见</option><option value="">回答问题可见</option></select></td></tr></table>'
            // });
            // layer.prompt({title: '请输入相册名称', formType: 3}, function (pass, index) {
            //     layer.close(index);
            //
            //
            //     layer.prompt({title: '随便写点啥，并确认', formType: 2}, function (text, index) {
            //         layer.close(index);
            //         layer.msg('演示完毕！您的口令：' + pass + '<br>您最后写下了：' + text);
            //     });
            // });
        }, remove: function (images) {
            console.log(images);
            layerConfirm('确认删除' + images.length + "个相册？", function () {
                var $body = $('body:eq(0)');
                var count = 0;
                var albumId = -1;
                for (var i = 0; i < images.length; i++) {
                    albumId = getId(images[i].find('img').attr('lazy'), 1);
                    if (albumId !== -1) {
                        var fun = function () {
                            var index = i;
                            $.ajax({
                                url: albumId + '/removeAlbum',
                                dataType: 'json',
                                success: function () {
                                    count++;
                                    console.log('count = ' + count + "  index = " + index + "   " + images.length);
                                    if (index === images.length - 1) {
                                        show(count + '个相册删除成功', '刷新页面', function () {
                                            location.reload();
                                        })
                                    } else
                                        $('body:eq(0)').dequeue('remove');
                                }
                            })
                        };
                        $body.queue('remove', fun);
                    }
                    $body.dequeue('remove')
                }
            });
        }
    });
}
function loadCreate(type) {
    var keyup = function () {
        $(this).next().find('.count').html($(this).val().length);
    };
    $('#createTable').remove();
    $('body').append('<div id="createTable" style="display: none">' +
        '<table id="form"><tr><td class="col-left">相册名称：</td>' +
        '<td class="col-right"><input type="text" maxlength="30" name="name"><span>' +
        '<span class="count">0</span>/30</span></td> </tr> <tr>' +
        '<td class="col-left">相册描述：</td><td class="col-right">' +
        '<textarea maxlength="200" name="content"></textarea><span><span class="count">0</span>/200</span>' +
        '</td></tr><tr><td class="col-left">分类：</td><td class="col-right">' +
        '<select name="type" id=""><option value="0">类别1</option><option value="1">类别2</option>' +
        '</select></td></tr><tr><td class="col-left">权限：</td><td class="col-right">' +
        '<select name="authority" id=""><option value="0">所有人可见</option>' +
        '<option value="1">仅自己可见</option><option value="2">回答问题可见</option>' +
        '</select></td></tr></table><div style="text-align: center;margin-bottom: 8px">' +
        '<input class="btn" type="button" value="确定"/></div></div>');
    $('#form  select[name=authority]').on('change', function () {
        if ($(this).val() === 2 || $(this).val() === "2") {
            console.log('添加');
            $('#form tbody').append('<tr><td class="col-left">问题：</td><td class="col-right"><input maxlength="30" name="question" type="text"><span><span class="count">0</span>/30</span></td> </tr>');
            $('#form tbody').append('<tr><td class="col-left">答案：</td><td class="col-right"><input maxlength="30" name="answer" type="text"><span><span class="count">0</span>/30</span></td> </tr>');
            $('#form input[name=question]').on('keyup', keyup)
            $('#form input[name=answer]').on('keyup', keyup)
        } else {
            $('#form input[name=question]').parent().parent().remove();
            $('#form input[name=answer]').parent().parent().remove();
        }
    });
    $('#form .col-right:eq(0) input[type=text]').on('keyup', keyup).trigger('keyup');
    $('#form .col-right:eq(1) textarea').on('keyup', keyup).trigger('keyup');
    $('#createTable input[type=button]').on('click', function () {
        var name = $('[name=name]').eq(0).val();
        if (name === '' || name.length <= 0 || name > 30) {
            show('相册名称应该在1-30个字符内');
            return;
        }
        var content = $('[name=content]').eq(0).val();
        if (content.length < 0 || content > 30) {
            show('相册描述应该在0-30个字符内');
            return;
        }
        var question = $('[name=question]');
        if (question.length > 0) {
            if (question.eq(0).val() === '' || question.eq(0).val().length < 0 || question.eq(0).val().length > 30) {
                show('问题应该在1-30个字符内');
                return;
            }
        }

        var answer = $('[name=answer]');
        if (answer.length > 0) {
            if (answer.eq(0).val() === '' || answer.eq(0).val().length < 0 || answer.eq(0).val().length > 30) {
                show('答案应该在1-30个字符内');
                return;
            }
        }
        // var type = $('[name=name]').val();
        // if (name.length <= 0 || name > 30) {
        //     show('相册名称应该在1-30个字符');
        //     return;
        // }
        // var name = $('[name=name]').val();
        // if (name.length <= 0 || name > 30) {
        //     show('相册名称应该在1-30个字符');
        //     return;
        // }
        var data = {};
        if (question.length > 0 && answer.length) {
            data = {
                name: name.trim(),
                content: content.trim(),
                type: $('[name=type]').val().trim(),
                authority: $('[name=authority]').val().trim(),
                question: question.eq(0).val().trim(),
                answer: answer.eq(0).val().trim()
            }
        } else {
            data = {
                name: name.trim(),
                content: content.trim(),
                type: $('[name=type]').val().trim(),
                authority: $('[name=authority]').val().trim()
            };
        }
        var $this = $(this);
        if (type) {
            ajax({
                url: type.id + '/changeAlbum',
                data: data,
                success: function (result) {
                    $this.prop('disabled', false).removeClass('btn-disabled');
                    if (result.status === 0) {
                        show('修改相册信息成功', '刷新页面', function () {
                            location.reload();
                        })
                    } else if (result.status === 1)
                        show('修改相册信息失败');
                },
                complete: function () {
                    $this.prop('disabled', true).addClass('btn-disabled');
                }, error: function () {
                    $this.prop('disabled', false).removeClass('btn-disabled');
                }
            })

        } else {
            ajax({
                url: 'createAlbum',
                type: 'post',
                data: data,
                success: function (result) {
                    $this.prop('disabled', false);
                    if (result.status === 0) {
                        $('.image_group').append('<div class="image_item" onclick="location.href=\'' + result.data.album.id
                            + '/\'" style="position: relative;">' +
                            ' <div class="image">' +
                            '<img src="http://static.inkroom.cn/image/img/no.jpg" alt="" title="' + result.data.album.content
                            + '(共' + result.data.album.number + '张)"><span class="count not_select">'
                            + result.data.album.number + '</span></div><div class="name">'
                            + result.data.album.name + '</div></div>');
                        loadAlbumMenu();
                        show('创建相册成功！', null, null);
                        clearCreate($('#createTable'));
                        $('#none').remove();
                    } else if (result.status === 1) {
                        show('创建相册失败！', null, null)
                    }

                },
                complete: function () {
                    $this.prop('disabled', true);
                }, error: function () {
                    $this.prop('disabled', false);
                }
            });
        }

    })
}

function answerQuestion(albumId, questionId) {
    ajax({
        url: albumId + '/' + questionId + '/getQuestion',
        dataType: 'json',
        success: function (result) {
            if (result.status === 0) {
                layer.prompt({title: '问题：'+result.data.question, formType: 3}, function (text, index) {
                    layer.close(index);
//                            location.href = albumId + "?answer=" + text;
                    ajax({
                        url: albumId + '/' + questionId + '/checkQuestion',
                        dataType: 'json',
                        data: {answer: text},
                        success: function (result) {
                            if (result.status === 0) {
                                show('回答正确', '进入相册', function () {
                                    location.href = albumId + "/";
                                });
                                setTimeout(function () {
                                    location.href = albumId + "/";
                                }, 2000);
                            } else if (result.status === 1) {
                                show('回答错误！');
                            }
                        }
                    })
                });
            } else if (result.status === 1) {
                show('未获取到问题！');
            }
        }
    })
}
