// Generated by CoffeeScript 1.9.3
(function() {
	$(function() {
		/*var brick;
		brick = "<div class='brick small'><div class='delete'>&times;</div></div>";*/
		/*$(document).on("click touchend", ".gridly .brick", function(event) {
			var $this, size;
			event.preventDefault();
			event.stopPropagation();
			$this = $(this);
			$this.toggleClass('small');
			$this.toggleClass('large');
			if ($this.hasClass('small')) {
				size = 140;
			}
			if ($this.hasClass('large')) {
				size = 300;
			}
			$this.data('width', size);
			$this.data('height', size);
			return $('.gridly').gridly('layout');
		});
		$(document).on("click", ".gridly .delete", function(event) {
			var $this;
			event.preventDefault();
			event.stopPropagation();
			$this = $(this);
			$this.closest('.brick').remove();
			return $('.gridly').gridly('layout');
		});
		$(document).on("click", ".add", function(event) {
			event.preventDefault();
			event.stopPropagation();
			$('.gridly').append(brick);
			return $('.gridly').gridly();
		});*/

		var reordering = function($elements) {
			// Called before the drag and drop starts with the elements in their
			// starting position.
			// alert('start');
		};

		var reordered = function($elements) {
			// Called after the drag and drop ends with the elements in their
			// ending position.
			var sorts = "";
			// 当前对象
			var currentObj = this.reordered.arguments[0];
			if (currentObj == null || currentObj == undefined || currentObj.length == 0){
				top.toastr.error('无可用菜单！','菜单排序');
				return;
			}
			var seq = 10;
			$(currentObj).each(function(index){
				var tmpText = $(this).text();
				if (tmpText != null && tmpText != ''){
					var tmpArray = tmpText.split('|');
					sorts = sorts + tmpArray[1] + '-' + seq + ",";
					seq = seq + 10;
				}
			});
			if (sorts == ''){
				Common.showMsg('菜单排序','无可用菜单！');
				return;
			}
			sorts = sorts.substring(0,sorts.lastIndexOf(",",sorts.length - 1));
			/*var currentId = currentObj[0].id;

			alert('拖动对象：' + currentId);

			var arr = $elements;
			// 前一个对象
			var prevObj;
			// 后一个对象
			var afterObj;

			for (i = 0; i < arr.length; i++) {
				if (arr[i].id == currentId) {
					if (i > 0) {
						prevObj = arr[i - 1];
					}
					if (i + 1 < arr.length) {
						afterObj = arr[i + 1];
					}
				}
			}

			if (prevObj != undefined) {
				alert('前一个对象：' + prevObj.id)
			}
			if (afterObj != undefined) {
				alert('后一个对象：' + afterObj.id);
			}*/

		};

		return $('.gridly').gridly({
			callbacks : {
				reordering : reordering,
				reordered : reordered
			}
		});

		// return $('.gridly').gridly();
	});

}).call(this);
