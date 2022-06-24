var tabFilter = "xbs_tab";
layui.config({
  base: 'js/',
}).use(['element', 'tabrightmenu'], function() {
  var $ = layui.jquery;
  var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
  var rightmenu_ = layui.tabrightmenu;
  //触发事件
  var active = {
    //在这里给active绑定几项事件，后面可通过active调用这些事件
    tabAdd: function(id, title, url) {
      //    var str='<span style="color:#c00">{title}</span>';
      //    title=str.format({title:title});
      //关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
      var content = '<iframe data-frameid="{id}" scrolling="auto" frameborder="0" src="{url}" style="width:100%;height:100%;"></iframe>';
      content = content.format({ id: id, url: url });
      element.tabAdd(tabFilter, { id: id, title: title, content: content })
      resizeIFrameWitdhHeight(); //计算ifram层的大小
    },
    tabChange: function(id) {
      //切换到指定Tab项
      element.tabChange(tabFilter, id); //根据传入的id传入到指定的tab项
    },
    tabDelete: function(id) {
      element.tabDelete(tabFilter, id); //删除
    },
    tabDeleteAll: function(ids) { //删除所有
      $.each(ids, function(i, item) {
        element.tabDelete(tabFilter, item); //ids是一个数组，里面存放了多个id，调用tabDelete方法分别删除
      })
    }
  };

  //当点击有nav-menu-item属性的标签时，即左侧菜单栏中内容 ，触发点击事件
  $('.nav-menu-item').on('click', function() {
    var menuItem = $(this);
    var menuId = menuItem.attr('data-id');
    var menuUrl = menuItem.attr('data-url');
    var menuTitle = menuItem.text();
    openTab(menuId, menuUrl, menuTitle);
  });

  function openTab(menuId, menuUrl, menuTitle) {
    var layIds = $(".layui-tab-title li[lay-id]");
    //判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
    if(layIds.length <= 0) {
      //如果比零小，则直接打开新的tab项
      active.tabAdd(menuId, menuTitle, menuUrl);
    } else {
      //否则判断该tab项是否以及存在
      var isOpen = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
      $.each(layIds, function() {
        //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
        if($(this).attr("lay-id") == menuId) {
          isOpen = true;
        }
      });
      if(isOpen == false) {
        //标志为false 新增一个tab项
        active.tabAdd(menuId, menuTitle, menuUrl);
      }
    }
    //最后不管是否新增tab，最后都转到要打开的选项页面上
    active.tabChange(menuId);
  }

  rightmenu_.render({
    container: '#nav_xbs_tab',
    filter: 'xbs_tab',
    navArr: [
      { eventName: 'refresh', title: '刷新当前页' },
      { eventName: 'closethis', title: '关闭当前页' },
      { eventName: 'closeall', title: '关闭所有页' },
      { eventName: 'closeothers', title: '关闭其它页' },
      { eventName: 'closeothers', title: '关闭其它页' },
      { eventName: 'closeleft', title: '关闭左侧全部' },
      { eventName: 'closeright', title: '关闭右侧全部' },
      { eventName: 'openNewTab', title: '在新标签页打开' }
    ],
  });
  //打开默认tab页
  for(var i=0;i<tabs.length;i++){
    var tab=tabs[i];
    openTab(tab.id,tab.uri,tab.title);
  }
});

function resizeIFrameWitdhHeight() {
  //var h = $(window).height() - 41 - 10 - 60 - 10 - 44 - 10;
  //$("iframe").css("height", h + "px");
}

$(window).resize(function() {
  resizeIFrameWitdhHeight();
});

function logout() {
  saveToSessionStorage({});
  window.location.href = window.location.origin + projectName + '/api/users/logout';
}