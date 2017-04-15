
/**
*
* @param _this 所点击的tab标签
* @param content_prefix tab标签所对应div的id前缀。注：这里要求所有的前缀必须一样。
* @param active 所要激活div的id最后的数字。注：这里要求数字必须从零开始，依次增1.
*/
function tabSwitch2(_this,content_prefix,active) {
var tabs = document.getElementsByName(_this.name);
var number = tabs.length;
for (var i=0; i < number; i++) {
var tab = tabs[i];
tab.className = "";
document.getElementById(content_prefix+i).style.display = 'none';
}
_this.className = "easytab_active";
document.getElementById(content_prefix+active).style.display = 'block';
} 

/**
*
* @param _this 所点击的tab标签
* @param content_prefix tab标签所对应div的id前缀。注：这里要求所有的前缀必须一样。
* @param active 所要激活div的id最后的数字。注：这里要求数字必须从零开始，依次增1.
*/
function tabSwitch2UL(_this,content_prefix,active) {
var tabs = document.getElementsByName(_this.name);
var number = tabs.length;
for (var i=0; i < number; i++) {
var tab = tabs[i];
tab.className = "";
document.getElementById(content_prefix+i).style.display = 'none';
}
_this.className = "easytab_ul_active";
document.getElementById(content_prefix+active).style.display = 'block';
} 