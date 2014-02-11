function theme_setActiveStyleSheet(title) {
  var i, a, t, main;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    try { t = a.getAttribute("title"); } catch (Exception) { t = ""; }
    if (t != null && t.indexOf("theme_") == 0) {
      if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
        a.disabled = true;
        if(a.getAttribute("title") == title) a.disabled = false;
      }
    }
  }
}

function theme_getActiveStyleSheet() {
  var i, a, t;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    try { t = a.getAttribute("title"); } catch (Exception) { t = ""; }
    if (t != null && t.indexOf("theme_") == 0) {
      if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title") && !a.disabled) return a.getAttribute("title");
    }
  }
  return null;
}

function theme_getPreferredStyleSheet() {
  var i, a, t;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    try { t = a.getAttribute("title"); } catch (Exception) { t = ""; }
    if (t != null && t.indexOf("theme_") == 0) {
      if(a.getAttribute("rel").indexOf("style") != -1
         && a.getAttribute("rel").indexOf("alt") == -1
         && a.getAttribute("title")
         ) return a.getAttribute("title");
    }
  }
  return null;
}

function theme_createCookie(name,value,days) {
  if (days) {
    var date = new Date();
    date.setTime(date.getTime()+(days*24*60*60*1000));
    var expires = "; expires="+date.toGMTString();
  }
  else expires = "";
  document.cookie = name+"="+value+expires+"; path=/";
}

function theme_readCookie(name) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(';');
  for(var i=0;i < ca.length;i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
  }
  return null;
}

function theme_toggleActiveStyleSheet() {
  var s1 = theme_getActiveStyleSheet();
  if (s1 == "" || s1 == "theme_dyslex") theme_setActiveStyleSheet("theme_default");
  else return theme_setActiveStyleSheet("theme_dyslex");
}
