		var newpw = null;
    	var agin = null;
    	var ispass = false;
    	var infodialog = null;
  
    	function showdialog(e){
    		var htm='<div class="pwcontanner"><div class="beforepw"><p class="pwtitle">ԭ����:</p><input type="password" class="pwinput initpw" value="" onblur="checkInitPw(this)" onkeydown="return banInputSapce(event);" onclick="pwclick(this)"/><div class="pwtip"></div></div>'+
        	'<div class="beforepw"><p class="pwtitle">������:</p><input type="password" class="pwinput newpw" value="" onkeydown="return banInputSapce(event);" onblur="newpwhand(this)"/><div class="pwtip newtip">(6-12λ���֡���ĸ���)</div></div>'+
        	'<div class="beforepw"><p class="pwtitle">�ٴ���������:</p><input type="password" class="pwinput aginpw" value="" onkeydown="return banInputSapce(event);" onkeyup="agininput(this)"/><div class="pwtip"></div></div></div>';
    		$.dialog({
    		titleText:'�����޸�',
        	type : 'confirm',
        	dialogClass:'pwdialog',
        	buttonClass:{
    			ok : 'okbutton',
    			cancel : 'cancelbutton'
			}, 
			buttonText : {
	            ok : 'ȷ��',
	            cancel : 'ȡ��'
	        },
			contentHtml : htm,
			onClickOk : function(){
           		$.each($('.pwinput'),function(index,value){
           			
           			var v = $('.pwinput').eq(index).val();
           			alert(v);
           			if(v == ''){
           				$('.pwtip').eq(index).css("color",'red');
           				$('.pwtip').eq(index).html("����������");
           				$('.pwinput').eq(index).click(function(e){
           					if(index == 1)
							$('.pwtip').eq(index).html('(6-12λ���֡���ĸ���)');
							else
							$('.pwtip').eq(index).html('');
						});
           				$.dialog.show();
           				return;
           			}
           		})
           		if(newpw != agin){
           				$('.pwtip').eq(2).html('<i class="fa fa-times-circle fa-lg"></i>�������벻һ��!');
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
				       infoText : 'ִ���С�',
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
							        infoText : '�޸ĳɹ�',
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
			$('.pwtip').eq(1).html('<i class="fa fa-times-circle fa-lg"></i>������6-12λ����!');
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
					if(text == 'ѧ��' || text == '����Ա'){
						$('.pwtip').eq(0).html('<i class="fa fa-check-square fa-lg"></i>');
						$('.pwtip').eq(0).css('color','green')
						ispass = true;
					}else if(text == 'LOGINFAILL'){
						$('.pwtip').eq(0).html('<i class="fa fa-times-circle fa-lg"></i>�������');
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