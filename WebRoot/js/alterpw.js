		var newpw = null;
    	var agin = null;
    	var ispass = false;
    	var infodialog = null;
  
    	function showdialog(e){
    		var htm='<div class="pwcontanner"><div class="beforepw"><p class="pwtitle">原密码:</p><input type="password" class="pwinput initpw" value="" onblur="checkInitPw(this)" onkeydown="return banInputSapce(event);" onclick="pwclick(this)"/><div class="pwtip"></div></div>'+
        	'<div class="beforepw"><p class="pwtitle">新密码:</p><input type="password" class="pwinput newpw" value="" onkeydown="return banInputSapce(event);" onblur="newpwhand(this)"/><div class="pwtip newtip">(6-12位数字、字母组成)</div></div>'+
        	'<div class="beforepw"><p class="pwtitle">再次输入密码:</p><input type="password" class="pwinput aginpw" value="" onkeydown="return banInputSapce(event);" onkeyup="agininput(this)"/><div class="pwtip"></div></div></div>';
    		$.dialog({
    		titleText:'密码修改',
        	type : 'confirm',
        	dialogClass:'pwdialog',
        	buttonClass:{
    			ok : 'okbutton',
    			cancel : 'cancelbutton'
			}, 
			buttonText : {
	            ok : '确定',
	            cancel : '取消'
	        },
			contentHtml : htm,
			onClickOk : function(){
           		$.each($('.pwinput'),function(index,value){
           			
           			var v = $('.pwinput').eq(index).val();
           			alert(v);
           			if(v == ''){
           				$('.pwtip').eq(index).css("color",'red');
           				$('.pwtip').eq(index).html("请输入密码");
           				$('.pwinput').eq(index).click(function(e){
           					if(index == 1)
							$('.pwtip').eq(index).html('(6-12位数字、字母组成)');
							else
							$('.pwtip').eq(index).html('');
						});
           				$.dialog.show();
           				return;
           			}
           		})
           		if(newpw != agin){
           				$('.pwtip').eq(2).html('<i class="fa fa-times-circle fa-lg"></i>密码输入不一致!');
						$('.pwtip').eq(2).css('color','red');
						$.dialog.show();
           		}
       		},
       		onClosed : function(){
       			if(ispass && newpw == agin){
       				ispass = false;
       				agin = null;
           			infodialog = $.dialog({
				       type : 'info',
				       infoText : '执行中…',
				       infoIcon : '../dialog/images/icon/loading.gif'     
			    	});
			    	
           			$.ajax({
           				type :"POST",
           				data:{operat:'alterpw',password:newpw},
           				url:'../frontservlet',
           				dataType:'text',
           				success:function(text,textStatus){				
           					if(text == 'SUCCESS'){
           						 infodialog.dialog.update({
							        autoClose : 1500,
							        infoText : '修改成功',
							        infoIcon : '../dialog/images/icon/success.png'
						        });
           					}
           				}
           			});	
       			}	
    	}});
	}
	function newpwhand(ele){
		newpw = $(ele).val();
		if(newpw.length < 6  || newpw.length > 12){
			$('.pwtip').eq(1).html('<i class="fa fa-times-circle fa-lg"></i>请输入6-12位密码!');
			$('.pwtip').eq(1).css('color','red')
			$.dialog.show();
		}else{
			$('.pwtip').eq(1).html('<i class="fa fa-check-square fa-lg"></i>');
			$('.pwtip').eq(1).css('color','green')
		}
		
	}
	function pwclick(ele){
		$('.pwtip').eq(0).html('');
	}
	function checkInitPw(ele){
		var val = $(ele).val();
		if(val != ''){
			$('.pwtip').eq(0).html('<i class="fa fa-spinner fa-pulse fa-lg"></i>');
			$.ajax({
				type:'POST',
				data:{operat:'login',password:$(ele).val()},
				url:'../frontservlet',
				dataType:'text',
				success:function(text,textStatus){
					if(text == '学生' || text == '管理员'){
						$('.pwtip').eq(0).html('<i class="fa fa-check-square fa-lg"></i>');
						$('.pwtip').eq(0).css('color','green')
						ispass = true;
					}else if(text == 'LOGINFAILL'){
						$('.pwtip').eq(0).html('<i class="fa fa-times-circle fa-lg"></i>密码错误');
						$('.pwtip').eq(0).css('color','red')
						ispass = false;
					}
				}
			})
		}
	}
	function agininput(ele){
		agin = $(ele).val();
		if(newpw == agin){
			$('.pwtip').eq(2).html('<i class="fa fa-check-square fa-lg"></i>');
			$('.pwtip').eq(2).css('color','green')
			
		}else
			$('.pwtip').eq(2).html('');
			
	}
	function banInputSapce(e)
	{
		var keynum;
		if(window.event) // IE
		{
		keynum = e.keyCode
		}
		else if(e.which) // Netscape/Firefox/Opera
		{
		keynum = e.which
		}
		//alert(keynum);
		if(keynum == 32){
		return false;
		}
		return true;
	}