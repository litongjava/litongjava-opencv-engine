/**
 * 弹出没有登录提示
 */
function toLogin(layer) {
  //提示,调整到登录界面
  layer.alert('检测到没有登录,请重新登录', { skin: 'layui-layer-molv', closeBtn: 0, },
    function() {
      layer.close(layui.index);
      saveToSessionStorage({});
      var url = projectName + '/login.html';
      window.parent.open(url, '_self');
    });
}

function loadFromSessionStorage() {
  const s = sessionStorage.getItem('CCTV-USER');
  if(!s)
    return null;

  try {
    return JSON.parse(s);
  } catch(e) {
    return null;
  }
}

function hasPermission(pattern) {
  const userInfo = loadFromSessionStorage();
  if(userInfo.id === 1)
    return true; // TODO: this is for testing purpose

  if(!pattern) return true;

  return userInfo.permissions.findIndex((per) => per === pattern) > -1;
  return true;
}

function saveToSessionStorage(user) {
  if(!user || !user.id)
    sessionStorage.removeItem('CCTV-USER');
  else {
    sessionStorage.setItem('CCTV-USER', JSON.stringify(user));
  }
}

function getMine($, resolve) {
  let user = loadFromSessionStorage();
  if(user && user.id > 0) {
    $.ajax({
      url: projectName+'/api/users/mine',
      type: "get",
      beforeSend: function(xhr) {
        xhr.setRequestHeader("X-LT-User-Token", user.id);
      },
      success: (data, status, res) => {
        user = data.data;
        $.ajax({
          url: projectName+'/api/users?method=getRelationRole&uid=' + user.id,
          type: "get",
          beforeSend: function(xhr) {
            xhr.setRequestHeader("X-LT-User-Token", user.id);
          },
          success: (data, status, res) => {
            const roles = data.data.map((d) => d.roles_id).join(',');
            $.ajax({
              url: projectName+'/api/role?method=getPermission&role=' + roles,
              type: "get",
              beforeSend: function(xhr) {
                xhr.setRequestHeader("X-LT-User-Token", user.id);
              },
              success: (data, status, res) => {
                user.permissions = data.data.map((d) => d.permissions_code);
                saveToSessionStorage(user);
                resolve();
              }
            });
          }
        });
      }
    });
  }
}