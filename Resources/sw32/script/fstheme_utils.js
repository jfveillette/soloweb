function setFontsizeStylesheet() {
  var cookie = fontSize_readCookie("sw_fontsize");
  var title = cookie ? cookie : fontSize_getPreferredStyleSheet();
  fontSize_setActiveStyleSheet(title);
}

function setThemeStylesheet() {
  var cookie = theme_readCookie("sw_dyslex");
  var title = cookie ? cookie : theme_getPreferredStyleSheet();
  theme_setActiveStyleSheet(title);
}

function saveFontsizeStylesheet() {
  var title = fontSize_getActiveStyleSheet();
  fontSize_createCookie("sw_fontsize", title, 365);
}

function saveThemeStylesheet() {
  var title = theme_getActiveStyleSheet();
  theme_createCookie("sw_dyslex", title, 365);
}

window.onload = function(e) {
  setFontsizeStylesheet();
  setThemeStylesheet();
}
  
window.onunload = function(e) {
  saveFontsizeStylesheet();
  saveThemeStylesheet();
}  

setFontsizeStylesheet();
setThemeStylesheet();
