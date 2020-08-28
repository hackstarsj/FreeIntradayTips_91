$('a.tv-screener__symbol.apply-common-tooltip').removeAttr('href');
$(document).on('click',"a.tv-screener__symbol.apply-common-tooltip",function(event){
event.preventDefault()

   var symbol=$(this).text();
   console.log(symbol);
   Android.alertJson(symbol);
})
$(document).on('click',".tv-screener-table__result-row",function(event){
event.preventDefault()

   var symbol=$(this).data("symbol");
   console.log(symbol);
   Android.alertJson(symbol);
})