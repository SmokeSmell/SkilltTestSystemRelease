$(document).ready(function(){
	
    var num;
    $('.nav-main>li[id]').hover(function(){
       
        $(this).children().removeClass().addClass('hover-up');
        
        var Obj = $(this).attr('id');
        num = Obj.substring(3, Obj.length);
        $('#box-'+num).slideDown(300);
    },function(){
        
        $(this).children().removeClass().addClass('hover-down');
        
        $('#box-'+num).hide();
    });

    $('.hidden-box').hover(function(){
       
        $('#li-'+num).children().removeClass().addClass('hover-up');
        $(this).show();
    },function(){
        $(this).slideUp(200);
        $('#li-'+num).children().removeClass().addClass('hover-down');
    });
     $('#changepw').click(function(e){showdialog(e)});
});
