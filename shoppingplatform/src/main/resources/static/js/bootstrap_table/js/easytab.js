
/**
*
* @param _this �������tab��ǩ
* @param content_prefix tab��ǩ����Ӧdiv��idǰ׺��ע������Ҫ�����е�ǰ׺����һ����
* @param active ��Ҫ����div��id�������֡�ע������Ҫ�����ֱ�����㿪ʼ��������1.
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
* @param _this �������tab��ǩ
* @param content_prefix tab��ǩ����Ӧdiv��idǰ׺��ע������Ҫ�����е�ǰ׺����һ����
* @param active ��Ҫ����div��id�������֡�ע������Ҫ�����ֱ�����㿪ʼ��������1.
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