+function ($) {
	var SummerBox = function (element) {
		this.elem = $(element);

		this.init();
	};

	SummerBox.prototype.init = function () {
		this.switch(); //盒子点击事件

		this.pointTouch(); //四个点的点击事件

		this.showMore();//显示更多点击事件

		this.drawSummerArrow();//划线

		this.titleTouch();//拖动

		this.doubleClick();//双击
	};
	
	function rebuildPointPosition( boxObj ) {
		var $this = $(boxObj);
		var curBoxLeft = $this.position().left;
		var curBoxTop = $this.position().top;
		var curBoxWidth = $this.width();
		var curBoxHeight = $this.height();
		var topPoint = $(".circle-top");
		var rightPoint = $(".circle-right");
		var bottomPoint = $(".circle-bottom");
		var leftPoint = $(".circle-left");
		var topRightPoint = $(".circle-top-right");
		var topLeftPoint = $(".circle-top-left");
		var bottomRightPoint = $(".circle-bottom-right");
		var bottomLeftPoint = $(".circle-bottom-left");

		topPoint.css("top", (curBoxTop - 10 + 2) + "px");
		topPoint.css("left", (curBoxWidth/2) + curBoxLeft + "px" );
		rightPoint.css("top", (curBoxTop + curBoxHeight/2) + "px");
		rightPoint.css("left", (curBoxWidth + curBoxLeft + 2 ) + "px" );
		bottomPoint.css("top", (curBoxTop + curBoxHeight + 2) + "px");
		bottomPoint.css("left", ((curBoxWidth/2) + curBoxLeft) + "px" );
		leftPoint.css("top", (curBoxTop + curBoxHeight/2) + "px");
		leftPoint.css("left", (curBoxLeft - 10 + 2 ) + "px" );
		topRightPoint.css("top", (curBoxTop - 10 + 2) + "px");
		topRightPoint.css("left", curBoxWidth + curBoxLeft + "px");
		topLeftPoint.css("top", (curBoxTop - 10 + 2) + "px");
		topLeftPoint.css("left", (curBoxLeft - 10 + 2 ) + "px");
		bottomRightPoint.css("top", (curBoxTop + curBoxHeight + 2) + "px");
		bottomRightPoint.css("left", (curBoxWidth + curBoxLeft) + "px");
		bottomLeftPoint.css("top", (curBoxTop + curBoxHeight + 2) + "px");
		bottomLeftPoint.css("left", (curBoxLeft - 10 + 2 ) + "px");
	}

	function drawPointsArrow( ctx, points, theta, headlen, width, color ){
		theta = typeof(theta) != 'undefined' ? theta : 30;
		headlen = typeof(headlen) != 'undefined' ? headlen : 10;
		width = typeof(width) != 'undefined' ? width : 1;
		color = typeof(color) != 'undefined' ? color : '#000';

		// 计算各角度和对应的P2,P3坐标
		var angle = 0,
			angle1 = 0,
			angle2 = 0,
			topX = 0,
			topY = 0,
			botX = 0,
			botY = 0,
			arrowX = 0,
			arrowY = 0;

		//第一条线
		ctx.beginPath();

		for( var i = 0, len = points.length; i < len; i++ ){
			if( i > 0 ){
				ctx.moveTo(points[i-1].x, points[i-1].y);
				ctx.lineTo(points[i].x, points[i].y);
			}

			if( i == (len - 1) ){
				//末尾点
				angle = Math.atan2(points[i-1].y - points[i].y, points[i-1].x - points[i].x) * 180 / Math.PI;
				angle1 = (angle + theta) * Math.PI / 180;
				angle2 = (angle - theta) * Math.PI / 180;
				topX = headlen * Math.cos(angle1);
				topY = headlen * Math.sin(angle1);
				botX = headlen * Math.cos(angle2);
				botY = headlen * Math.sin(angle2);

				arrowX = points[0].x - topX;
				arrowY = points[0].y - topY;
				ctx.moveTo(arrowX, arrowY);

				arrowX = points[len-1].x + topX;
				arrowY = points[len-1].y + topY;
				ctx.moveTo(arrowX, arrowY);
				ctx.lineTo(points[len-1].x, points[len-1].y);
				arrowX = points[len-1].x + botX;
				arrowY = points[len-1].y + botY;
				ctx.lineTo(arrowX, arrowY);
			}
		}

		ctx.strokeStyle = color;
		ctx.lineWidth = width;
		ctx.stroke();

		//第二条线
		ctx.beginPath();

		for( var i = 0, len = points.length; i < len; i++ ){
			if (i > 0) {
				ctx.moveTo(points[i - 1].x, points[i - 1].y);
				ctx.lineTo(points[i].x, points[i].y);
			}
		}

		ctx.strokeStyle = 'rgba(0,0,0,0)';
		ctx.lineWidth = width + 5;
		ctx.stroke();
	}

	//出发点是右侧的点
	function drawArrow( ctx, fromX, fromY, toX, toY, fromBox, toBox, lineContainer, fromIndex, toIndex ) {
		//fromY 已确认
		//起点和终点的高粗差
		var heightDiff = toY - fromY; //终点和起点的高度差
		var widthDiff = toX - fromX; //终点和起点的横坐标差
		var pointsArr = []; //保存所经过的点
		var innerPointsArr = []; //保存线container内的点
		var lineCanvas = null;
		var lineContainerLeft = 0; //线container左边距
		var lineContainerTop = 0; //线container上边距
		var lineContainerWidth = 0; //线container左边距
		var lineContainerHeight = 0; //线container左边距
		//1.判断向左还是向右
		//2.确定fromX 的坐标
		//根据fromBox和toBox的位置确定线的走势
		//分为有toBox和没toBox的情况
		if( lineContainer ){
			lineContainer.data("lineInfo",{
				'canvas': ctx,
				'fromX': fromX,
				'fromY': fromY,
				'toX': toX,
				'toY': toY,
				'fromBoxId': fromBox.attr("id"),
				'toBoxId': toBox.attr("id"),
				'fromIndex': fromIndex,
				'toIndex': toIndex
			});
		}
		if( toBox ){
			if( toBox.position().left > (fromBox.position().left +  fromBox.width()/2) ){
				//以fromBox的右边为出发点 右连左
				if( widthDiff > 0 ){

					//终点在起点之下
					if( heightDiff > 0 ){
						/*
						*增加中间的两个转折点
						* */
						var turnTop = {
							x: ( toX - fromX ) / 2 + fromX,
							y: fromY
						};

						var turnBottom = {
							x: ( toX - fromX ) / 2 + fromX,
							y: toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( toX - fromX ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = fromX - 10;
							lineContainerTop = fromY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var turn1 = {
								x: fromX - lineContainerLeft ,
								y: fromY - lineContainerTop
							};

							var turn2 = {
								x: turnTop.x - lineContainerLeft,
								y: turnTop.y - lineContainerTop
							};

							var turn3 = {
								x: turnBottom.x - lineContainerLeft,
								y: turnBottom.y - lineContainerTop
							};

							var turn4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};
							innerPointsArr = [];
							innerPointsArr.push({"x":turn1.x, "y": turn1.y});
							innerPointsArr.push({"x":turn2.x, "y": turn2.y});
							innerPointsArr.push({"x":turn3.x, "y": turn3.y});
							innerPointsArr.push({"x":turn4.x, "y": turn4.y});

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}
					}
					else if( heightDiff < 0 ) {
						//终点在起点之上
						var turnTop = {
							x: ( toX - fromX ) / 2 + fromX,
							y: toY
						};

						var turnBottom = {
							x: ( toX - fromX ) / 2 + fromX,
							y: fromY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( toX - fromX ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = fromX - 10;
							lineContainerTop = toY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var turn1 = {
								x: fromX - lineContainerLeft ,
								y: fromY - lineContainerTop
							};

							var turn2 = {
								x: turnBottom.x - lineContainerLeft,
								y: turnBottom.y - lineContainerTop
							};

							var turn3 = {
								x: turnTop.x - lineContainerLeft,
								y: turnTop.y - lineContainerTop
							};

							var turn4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};
							innerPointsArr = [];
							innerPointsArr.push({"x":turn1.x, "y": turn1.y});
							innerPointsArr.push({"x":turn2.x, "y": turn2.y});
							innerPointsArr.push({"x":turn3.x, "y": turn3.y});
							innerPointsArr.push({"x":turn4.x, "y": turn4.y});

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else {
							drawPointsArrow(ctx, pointsArr);
						}
					}
					else if( heightDiff === 0 ){

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( toX - fromX ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = fromX - 10;
							lineContainerTop = fromY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							innerPointsArr = [];
							innerPointsArr.push({"x":(fromX-lineContainerLeft), "y": (fromY-lineContainerTop)});
							innerPointsArr.push({"x":(toX-lineContainerLeft), "y": (toY-lineContainerTop)});

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else {
							drawPointsArrow(ctx, pointsArr);
						}
					}
				}
				else if( widthDiff <= 0 ) {
					//终点位于起点的左侧
					//终点位于起点之下
					if( heightDiff > 0 ) {

						//越过summerBox划线
						if( fromBox ) {
							//判断终点和fromBox低端的距离是否大于30
							if( toY - ( fromBox.position().top + fromBox.height() ) < 30 ) {

							} else if( toY - ( fromBox.position().top + $(fromBox).height() ) >= 30 ) {

								if( toBox ){
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : ( toBox.position().top - ( fromBox.position().top + fromBox.height() ) )/2 + ( fromBox.position().top + fromBox.height() )
									};

									var turn3 = {
										x: toX - 30,
										y: turn2.y
									};

									var turn4 = {
										x: turn3.x,
										y: toY
									};


									var arr = [];
									arr.push({"x":fromX, "y": fromY});
									arr.push({"x":turn1.x, "y": turn1.y});
									arr.push({"x":turn2.x, "y": turn2.y});
									arr.push({"x":turn3.x, "y": turn3.y});
									arr.push({"x":turn4.x, "y": turn4.y});
									arr.push({"x":toX, "y": toY});

									if( lineContainer ){
										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( turn1.y -  turn4.y ) + 20;
										lineContainerLeft = turn4.x - 10;
										lineContainerTop = turn1.y - 10;
										lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
										lineContainer.css("width", lineContainerWidth + "px");
										lineContainer.css("height", lineContainerHeight + "px");
										lineContainer.css("left", lineContainerLeft + "px");
										lineContainer.css("top", lineContainerTop + "px");
										lineContainer.find("canvas")[0].width = lineContainerWidth;
										lineContainer.find("canvas")[0].height = lineContainerHeight;

										var point1 = {
											x : fromX - lineContainerLeft,
											y : fromY - lineContainerTop
										};
										var point2 = {
											x: turn1.x - lineContainerLeft,
											y: turn1.y - lineContainerTop
										};
										var point3 = {
											x: turn2.x - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};
										var point4 = {
											x: turn3.x - lineContainerLeft,
											y: turn3.y - lineContainerTop
										};
										var point5 = {
											x: turn4.x - lineContainerLeft,
											y: turn4.y - lineContainerTop
										};
										var point6 = {
											x: toX - lineContainerLeft,
											y: toY - lineContainerTop
										};

										innerPointsArr = [];
										innerPointsArr.push({ "x": point1.x, "y": point1.y });
										innerPointsArr.push({ "x": point2.x, "y": point2.y });
										innerPointsArr.push({ "x": point3.x, "y": point3.y });
										innerPointsArr.push({ "x": point4.x, "y": point4.y });
										innerPointsArr.push({ "x": point5.x, "y": point5.y });
										innerPointsArr.push({ "x": point6.x, "y": point6.y });

										drawPointsArrow(lineCanvas, innerPointsArr);
									}else{
										drawPointsArrow(ctx, arr);
									}
								}
							}
						}
					}
					else if( heightDiff <= 0 ) {
						//终点位于起点之上

						if( fromBox ) {
							//判断终点和fromBox顶端端的距离是否大于30
							if( ( fromBox.position().top - toY ) < 30 ) {

							} else if( ( fromBox.position().top - toY ) >= 30 ) {
								if( toBox ) {
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : ( fromBox.position().top - ( toBox.position().top + toBox.height() ) )/2 + ( toBox.position().top + toBox.height() )
									};

									var turn3 = {
										x: toX - 30,
										y: turn2.y
									};

									var turn4 = {
										x: turn3.x,
										y: toY
									};

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":turn3.x, "y": turn3.y});
									pointsArr.push({"x":turn4.x, "y": turn4.y});
									pointsArr.push({"x":toX, "y": toY});

									if( lineContainer ){
										lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
										lineContainerHeight = Math.abs( turn1.y -  turn4.y ) + 20;
										lineContainerLeft = turn4.x - 10;
										lineContainerTop = turn4.y - 10;
										lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
										lineContainer.css("width", lineContainerWidth + "px");
										lineContainer.css("height", lineContainerHeight + "px");
										lineContainer.css("left", lineContainerLeft + "px");
										lineContainer.css("top", lineContainerTop + "px");
										lineContainer.find("canvas")[0].width = lineContainerWidth;
										lineContainer.find("canvas")[0].height = lineContainerHeight;

										var point1 = {
											x : fromX - lineContainerLeft,
											y : fromY - lineContainerTop
										};
										var point2 = {
											x: turn1.x - lineContainerLeft,
											y: turn1.y - lineContainerTop
										};
										var point3 = {
											x: turn2.x - lineContainerLeft,
											y: turn2.y - lineContainerTop
										};
										var point4 = {
											x: turn3.x - lineContainerLeft,
											y: turn3.y - lineContainerTop
										};
										var point5 = {
											x: turn4.x - lineContainerLeft,
											y: turn4.y - lineContainerTop
										};
										var point6 = {
											x: toX - lineContainerLeft,
											y: toY - lineContainerTop
										};

										innerPointsArr = [];
										innerPointsArr.push({ "x": point1.x, "y": point1.y });
										innerPointsArr.push({ "x": point2.x, "y": point2.y });
										innerPointsArr.push({ "x": point3.x, "y": point3.y });
										innerPointsArr.push({ "x": point4.x, "y": point4.y });
										innerPointsArr.push({ "x": point5.x, "y": point5.y });
										innerPointsArr.push({ "x": point6.x, "y": point6.y });

										drawPointsArrow(lineCanvas, innerPointsArr);
									}else{
										drawPointsArrow(ctx, pointsArr);
									}
								}
							}
						}
					}
				}
			}
			else if( (fromBox.position().left + fromBox.width()/2) < ( toBox.position().left + toBox.width()/2 ) ){
				//右连右

				//变为右连右
				fromX = fromBox.position().left + fromBox.width();
				toX = toBox.position().left + toBox.width();
				heightDiff = toY - fromY; //终点和起点的高度差
				widthDiff = toX - fromX; //终点和起点的横坐标差

				if( widthDiff > 0 ) {

					if( heightDiff > 0 ) {
						//之下
						var turn1 = {
							x: toX + 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y:toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turn1.x, "y": turn1.y});
						pointsArr.push({"x":turn2.x, "y": turn2.y});
						pointsArr.push({"x":toX, "y": toY});


						if( lineContainer ){
							lineContainerWidth = Math.abs( fromX - turn2.x ) + 20;
							lineContainerHeight = Math.abs( fromY -  turn2.y ) + 20;
							lineContainerLeft = fromX - 10;
							lineContainerTop = fromY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var point1 = {
								x : fromX - lineContainerLeft,
								y : fromY - lineContainerTop
							};
							var point2 = {
								x: turn1.x - lineContainerLeft,
								y: turn1.y - lineContainerTop
							};
							var point3 = {
								x: turn2.x - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};
							var point4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};

							innerPointsArr = [];
							innerPointsArr.push({ "x": point1.x, "y": point1.y });
							innerPointsArr.push({ "x": point2.x, "y": point2.y });
							innerPointsArr.push({ "x": point3.x, "y": point3.y });
							innerPointsArr.push({ "x": point4.x, "y": point4.y });

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}

					}else if( heightDiff <= 0 ) {
						//之上

						var turn1 = {
							x: toX + 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y:toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turn1.x, "y": turn1.y});
						pointsArr.push({"x":turn2.x, "y": turn2.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( fromX - turn2.x ) + 20;
							lineContainerHeight = Math.abs( fromY -  turn2.y ) + 20;
							lineContainerLeft = fromX - 10;
							lineContainerTop = turn2.y - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var point1 = {
								x : fromX - lineContainerLeft,
								y : fromY - lineContainerTop
							};
							var point2 = {
								x: turn1.x - lineContainerLeft,
								y: turn1.y - lineContainerTop
							};
							var point3 = {
								x: turn2.x - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};
							var point4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};

							innerPointsArr = [];
							innerPointsArr.push({ "x": point1.x, "y": point1.y });
							innerPointsArr.push({ "x": point2.x, "y": point2.y });
							innerPointsArr.push({ "x": point3.x, "y": point3.y });
							innerPointsArr.push({ "x": point4.x, "y": point4.y });

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}
					}
				}
			}
			else if( ((toBox.position().left + toBox.width()/2) < (fromBox.position().left + fromBox.width()/2)) && ((toBox.width()+toBox.position().left) > (fromBox.position().left + fromBox.width()/2) ) ){
				//左连左

				//变为左连左
				fromX = fromBox.position().left;
				toX = toBox.position().left;
				//起点和终点的高粗差
				widthDiff = toX - fromX; //终点和起点的横坐标差
				heightDiff = toY - fromY; //终点和起点的高度差

				if( widthDiff <= 0 ) {
					if( heightDiff > 0 ){
						//之下
						var turn1 = {
							x: toX - 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y:toY
						};
						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turn1.x, "y": turn1.y});
						pointsArr.push({"x":turn2.x, "y": turn2.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( fromX - turn2.x ) + 20;
							lineContainerHeight = Math.abs( fromY -  turn2.y ) + 20;
							lineContainerLeft = turn2.x - 10;
							lineContainerTop = fromY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var point1 = {
								x : fromX - lineContainerLeft,
								y : fromY - lineContainerTop
							};
							var point2 = {
								x: turn1.x - lineContainerLeft,
								y: turn1.y - lineContainerTop
							};
							var point3 = {
								x: turn2.x - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};
							var point4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};

							innerPointsArr = [];
							innerPointsArr.push({ "x": point1.x, "y": point1.y });
							innerPointsArr.push({ "x": point2.x, "y": point2.y });
							innerPointsArr.push({ "x": point3.x, "y": point3.y });
							innerPointsArr.push({ "x": point4.x, "y": point4.y });

							drawPointsArrow(lineCanvas, innerPointsArr);
						}
						else{
							drawPointsArrow(ctx, pointsArr);
						}

					}
					else if( heightDiff <= 0 ){
						//之上
						var turn1 = {
							x: toX - 30,
							y: fromY
						};
						var turn2 = {
							x: turn1.x,
							y: toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turn1.x, "y": turn1.y});
						pointsArr.push({"x":turn2.x, "y": turn2.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( fromX - turn2.x ) + 20;
							lineContainerHeight = Math.abs( fromY -  turn2.y ) + 20;
							lineContainerLeft = turn2.x - 10;
							lineContainerTop = turn2.y - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var point1 = {
								x : fromX - lineContainerLeft,
								y : fromY - lineContainerTop
							};
							var point2 = {
								x: turn1.x - lineContainerLeft,
								y: turn1.y - lineContainerTop
							};
							var point3 = {
								x: turn2.x - lineContainerLeft,
								y: turn2.y - lineContainerTop
							};
							var point4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};

							innerPointsArr = [];
							innerPointsArr.push({ "x": point1.x, "y": point1.y });
							innerPointsArr.push({ "x": point2.x, "y": point2.y });
							innerPointsArr.push({ "x": point3.x, "y": point3.y });
							innerPointsArr.push({ "x": point4.x, "y": point4.y });

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}
					}
				}
			}
			else if( (toBox.position().left + toBox.width()) < (fromBox.position().left + fromBox.width()) ){
				//以fromBox的左边为出发点 左连右
				if( widthDiff < 0 ) {

					//终点位于起点之下
					if( heightDiff > 0 ) {

						/*
						*增加中间的两个转折点
						* */
						var turnTop = {
							x: ( fromX - toX ) / 2 + toX,
							y: fromY
						};

						var turnBottom = {
							x: turnTop.x,
							y: toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( toX - fromX ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = toX - 10;
							lineContainerTop = fromY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var turn1 = {
								x: fromX - lineContainerLeft ,
								y: fromY - lineContainerTop
							};

							var turn2 = {
								x: turnTop.x - lineContainerLeft,
								y: turnTop.y - lineContainerTop
							};

							var turn3 = {
								x: turnBottom.x - lineContainerLeft,
								y: turnBottom.y - lineContainerTop
							};

							var turn4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};
							innerPointsArr = [];
							innerPointsArr.push({"x":turn1.x, "y": turn1.y});
							innerPointsArr.push({"x":turn2.x, "y": turn2.y});
							innerPointsArr.push({"x":turn3.x, "y": turn3.y});
							innerPointsArr.push({"x":turn4.x, "y": turn4.y});

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}
					}
					else if( heightDiff < 0 ) {
						//终点在起点之上
						var turnTop = {
							x: ( fromX - toX ) / 2 + toX,
							y: toY
						};

						var turnBottom = {
							x: turnTop.x,
							y: fromY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( toX - fromX ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = toX - 10;
							lineContainerTop = toY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var turn1 = {
								x: fromX - lineContainerLeft ,
								y: fromY - lineContainerTop
							};

							var turn3 = {
								x: turnTop.x - lineContainerLeft,
								y: turnTop.y - lineContainerTop
							};

							var turn2 = {
								x: turnBottom.x - lineContainerLeft,
								y: turnBottom.y - lineContainerTop
							};

							var turn4 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};
							innerPointsArr = [];
							innerPointsArr.push({"x":turn1.x, "y": turn1.y});
							innerPointsArr.push({"x":turn2.x, "y": turn2.y});
							innerPointsArr.push({"x":turn3.x, "y": turn3.y});
							innerPointsArr.push({"x":turn4.x, "y": turn4.y});

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}
					}
					else if( heightDiff === 0 ) {
						ctx.moveTo(fromX, fromY);
						ctx.lineTo(toX, toY);

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":toX, "y": toY});

						if( lineContainer ){
							lineContainerWidth = Math.abs( toX - fromX ) + 20;
							lineContainerHeight = Math.abs( toY -  fromY ) + 20;
							lineContainerLeft = toX - 10;
							lineContainerTop = toY - 10;
							lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
							lineContainer.css("width", lineContainerWidth + "px");
							lineContainer.css("height", lineContainerHeight + "px");
							lineContainer.css("left", lineContainerLeft + "px");
							lineContainer.css("top", lineContainerTop + "px");
							lineContainer.find("canvas")[0].width = lineContainerWidth;
							lineContainer.find("canvas")[0].height = lineContainerHeight;

							var turn1 = {
								x: fromX - lineContainerLeft ,
								y: fromY - lineContainerTop
							};

							var turn2 = {
								x: toX - lineContainerLeft,
								y: toY - lineContainerTop
							};
							innerPointsArr = [];
							innerPointsArr.push({"x":turn1.x, "y": turn1.y});
							innerPointsArr.push({"x":turn2.x, "y": turn2.y});

							drawPointsArrow(lineCanvas, innerPointsArr);
						}else{
							drawPointsArrow(ctx, pointsArr);
						}
					}
				}
				else if( widthDiff >= 0 ) {
					//终点位于起点的右侧

					//终点位于起点之下
					if( heightDiff > 0 ) {
						//判断终点和fromBox低端的距离是否大于30
						if( toY - ( fromBox.position().top + fromBox.height() ) < 30 ) {

						} else if( toY - ( fromBox.position().top + fromBox.height() ) >= 30 ) {

							if( toBox ){
								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : ( toBox.position().top - ( fromBox.position().top + fromBox.height() ) )/2 + ( fromBox.position().top + fromBox.height() )
								};

								var turn3 = {
									x: toX + 30,
									y: turn2.y
								};

								var turn4 = {
									x: turn3.x,
									y: toY
								};

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":turn3.x, "y": turn3.y});
								pointsArr.push({"x":turn4.x, "y": turn4.y});
								pointsArr.push({"x":toX, "y": toY});

								if( lineContainer ){
									lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
									lineContainerHeight = Math.abs( turn1.y -  turn4.y ) + 20;
									lineContainerLeft = turn1.x - 10;
									lineContainerTop = turn1.y - 10;
									lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
									lineContainer.css("width", lineContainerWidth + "px");
									lineContainer.css("height", lineContainerHeight + "px");
									lineContainer.css("left", lineContainerLeft + "px");
									lineContainer.css("top", lineContainerTop + "px");
									lineContainer.find("canvas")[0].width = lineContainerWidth;
									lineContainer.find("canvas")[0].height = lineContainerHeight;

									var point1 = {
										x : fromX - lineContainerLeft,
										y : fromY - lineContainerTop
									};
									var point2 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};
									var point3 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};
									var point4 = {
										x: turn3.x - lineContainerLeft,
										y: turn3.y - lineContainerTop
									};
									var point5 = {
										x: turn4.x - lineContainerLeft,
										y: turn4.y - lineContainerTop
									};
									var point6 = {
										x: toX - lineContainerLeft,
										y: toY - lineContainerTop
									};

									innerPointsArr = [];
									innerPointsArr.push({ "x": point1.x, "y": point1.y });
									innerPointsArr.push({ "x": point2.x, "y": point2.y });
									innerPointsArr.push({ "x": point3.x, "y": point3.y });
									innerPointsArr.push({ "x": point4.x, "y": point4.y });
									innerPointsArr.push({ "x": point5.x, "y": point5.y });
									innerPointsArr.push({ "x": point6.x, "y": point6.y });

									drawPointsArrow(lineCanvas, innerPointsArr);
								}else{
									drawPointsArrow(ctx, pointsArr);
								}
							}
						}
					}
					else if(  heightDiff <= 0 ) {
						//判断终点和fromBox顶端端的距离是否大于30
						if( ( fromBox.position().top - toY ) < 30 ) {

						} else if( fromBox.position().top - toY >= 30 ) {

							if( toBox ) {
								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : ( fromBox.position().top - ( toBox.position().top + toBox.height() ) )/2 + ( toBox.position().top + toBox.height() )
								};

								var turn3 = {
									x: toX + 30,
									y: turn2.y
								};

								var turn4 = {
									x: turn3.x,
									y: toY
								};

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":turn3.x, "y": turn3.y});
								pointsArr.push({"x":turn4.x, "y": turn4.y});
								pointsArr.push({"x":toX, "y": toY});

								if( lineContainer ){
									lineContainerWidth = Math.abs( turn1.x - turn4.x ) + 20;
									lineContainerHeight = Math.abs( turn1.y -  turn4.y ) + 20;
									lineContainerLeft = turn1.x - 10;
									lineContainerTop = turn4.y - 10;
									lineCanvas = lineContainer.find("canvas")[0].getContext("2d");
									lineContainer.css("width", lineContainerWidth + "px");
									lineContainer.css("height", lineContainerHeight + "px");
									lineContainer.css("left", lineContainerLeft + "px");
									lineContainer.css("top", lineContainerTop + "px");
									lineContainer.find("canvas")[0].width = lineContainerWidth;
									lineContainer.find("canvas")[0].height = lineContainerHeight;

									var point1 = {
										x : fromX - lineContainerLeft,
										y : fromY - lineContainerTop
									};
									var point2 = {
										x: turn1.x - lineContainerLeft,
										y: turn1.y - lineContainerTop
									};
									var point3 = {
										x: turn2.x - lineContainerLeft,
										y: turn2.y - lineContainerTop
									};
									var point4 = {
										x: turn3.x - lineContainerLeft,
										y: turn3.y - lineContainerTop
									};
									var point5 = {
										x: turn4.x - lineContainerLeft,
										y: turn4.y - lineContainerTop
									};
									var point6 = {
										x: toX - lineContainerLeft,
										y: toY - lineContainerTop
									};

									innerPointsArr = [];
									innerPointsArr.push({ "x": point1.x, "y": point1.y });
									innerPointsArr.push({ "x": point2.x, "y": point2.y });
									innerPointsArr.push({ "x": point3.x, "y": point3.y });
									innerPointsArr.push({ "x": point4.x, "y": point4.y });
									innerPointsArr.push({ "x": point5.x, "y": point5.y });
									innerPointsArr.push({ "x": point6.x, "y": point6.y });

									drawPointsArrow(lineCanvas, innerPointsArr);
								}else{
									drawPointsArrow(ctx, pointsArr);
								}
							}
						}
					}
				}
			}
		}
		else {
			if( ( toX - ( fromBox.position().left + fromBox.width()/2 ) ) > 0  ){
				if( widthDiff > 0 ){

					//终点在起点之下
					if( heightDiff > 0 ){
						/*
						*增加中间的两个转折点
						* */
						var turnTop = {
							x: ( toX - fromX ) / 2 + fromX,
							y: fromY
						};

						var turnBottom = {
							x: ( toX - fromX ) / 2 + fromX,
							y: toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":toX, "y": toY});

						drawPointsArrow(ctx, pointsArr);
					}
					else if( heightDiff < 0 ) {
						//终点在起点之上
						var turnTop = {
							x: ( toX - fromX ) / 2 + fromX,
							y: toY
						};

						var turnBottom = {
							x: ( toX - fromX ) / 2 + fromX,
							y: fromY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":toX, "y": toY});

						drawPointsArrow(ctx, pointsArr);
					}
					else if( heightDiff === 0 ){

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":toX, "y": toY});

						drawPointsArrow(ctx, pointsArr);
					}
				}
				else if( widthDiff <= 0 ) {
					//终点位于起点的左侧
					//终点位于起点之下
					if( heightDiff > 0 ) {

						//越过summerBox划线
						if( fromBox ) {
							//判断终点和fromBox低端的距离是否大于30
							if( toY - ( fromBox.position().top + fromBox.height() ) < 30 ) {
								if( toBox ) {


								}else {
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : fromBox.position().top + fromBox.height() + 30
									};

									var turn3 = {
										x: toX,
										y: turn2.y
									};

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":turn3.x, "y": turn3.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								}

							} else if( toY - ( fromBox.position().top + $(fromBox).height() ) >= 30 ) {

								if( toBox ){
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : ( toBox.position().top - ( fromBox.position().top + fromBox.height() ) )/2 + ( fromBox.position().top + fromBox.height() )
									};

									var turn3 = {
										x: toX - 30,
										y: turn2.y
									};

									var turn4 = {
										x: turn3.x,
										y: toY
									};


									var arr = [];
									arr.push({"x":fromX, "y": fromY});
									arr.push({"x":turn1.x, "y": turn1.y});
									arr.push({"x":turn2.x, "y": turn2.y});
									arr.push({"x":turn3.x, "y": turn3.y});
									arr.push({"x":turn4.x, "y": turn4.y});
									arr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, arr);
								}
								else {
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : fromBox.position().top + fromBox.height() + 30
									};

									var turn3 = {
										x: toX,
										y: turn2.y
									};

									//判断终点与转折点1的横坐标差与纵坐标差间的大小
									if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
										//纵坐标差大于横坐标差

										pointsArr = [];
										pointsArr.push({"x":fromX, "y": fromY});
										pointsArr.push({"x":turn1.x, "y": turn1.y});
										pointsArr.push({"x":turn2.x, "y": turn2.y});
										pointsArr.push({"x":turn3.x, "y": turn3.y});
										pointsArr.push({"x":toX, "y": toY});

										drawPointsArrow(ctx, pointsArr);
									} else {
										turn2.x = turn1.x;
										turn2.y = toY;

										pointsArr = [];
										pointsArr.push({"x":fromX, "y": fromY});
										pointsArr.push({"x":turn1.x, "y": turn1.y});
										pointsArr.push({"x":turn2.x, "y": turn2.y});
										pointsArr.push({"x":toX, "y": toY});

										drawPointsArrow(ctx, pointsArr);
									}
								}
							}
						} else {
							var turnBottom = {
								x: (fromX - toX)/2 + toX,
								y: fromY
							};
							var turnTop = {
								x: turnBottom.x,
								y: toY
							};

							pointsArr = [];
							pointsArr.push({"x":fromX, "y": fromY});
							pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
							pointsArr.push({"x":turnTop.x, "y": turnTop.y});
							pointsArr.push({"x":toX, "y": toY});

							drawPointsArrow(ctx, pointsArr);
						}
					}
					else if( heightDiff <= 0 ) {
						//终点位于起点之上

						if( fromBox ) {
							//判断终点和fromBox顶端端的距离是否大于30
							if( ( fromBox.position().top - toY ) < 30 ) {
								if( toBox ) {


								}else {
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : fromBox.position().top - 30
									};

									var turn3 = {
										x: toX,
										y: turn2.y
									};

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":turn3.x, "y": turn3.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								}
							} else if( ( fromBox.position().top - toY ) >= 30 ) {
								if( toBox ) {
									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : ( fromBox.position().top - ( toBox.position().top + toBox.height() ) )/2 + ( toBox.position().top + toBox.height() )
									};

									var turn3 = {
										x: toX - 30,
										y: turn2.y
									};

									var turn4 = {
										x: turn3.x,
										y: toY
									};

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":turn3.x, "y": turn3.y});
									pointsArr.push({"x":turn4.x, "y": turn4.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								}
								else {

									var turn1 = {
										x : fromX + 30,
										y : fromY
									};

									var turn2 = {
										x : turn1.x,
										y : fromBox.position().top - 30
									};

									var turn3 = {
										x: toX,
										y: turn2.y
									};

									//判断终点与转折点1的横坐标差与纵坐标差间的大小
									if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
										//纵坐标差大于横坐标差

										pointsArr = [];
										pointsArr.push({"x":fromX, "y": fromY});
										pointsArr.push({"x":turn1.x, "y": turn1.y});
										pointsArr.push({"x":turn2.x, "y": turn2.y});
										pointsArr.push({"x":turn3.x, "y": turn3.y});
										pointsArr.push({"x":toX, "y": toY});

										drawPointsArrow(ctx, pointsArr);
									} else {

										turn2.x = turn1.x;
										turn2.y = toY;

										pointsArr = [];
										pointsArr.push({"x":fromX, "y": fromY});
										pointsArr.push({"x":turn1.x, "y": turn1.y});
										pointsArr.push({"x":turn2.x, "y": turn2.y});
										pointsArr.push({"x":toX, "y": toY});

										drawPointsArrow(ctx, pointsArr);
									}
								}
							}
						} else {
							var turnTop = {
								x: (fromX - toX)/2 + toX,
								y: fromY
							};
							var turnBottom = {
								x: turnTop.x,
								y: toY
							};

							pointsArr = [];
							pointsArr.push({"x":fromX, "y": fromY});
							pointsArr.push({"x":turnTop.x, "y": turnTop.y});
							pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
							pointsArr.push({"x":toX, "y": toY});

							drawPointsArrow(ctx, pointsArr);
						}
					}
				}
			}
			else{
				if( widthDiff < 0 ) {

					//终点位于起点之下
					if( heightDiff > 0 ) {

						/*
						*增加中间的两个转折点
						* */
						var turnTop = {
							x: ( fromX - toX ) / 2 + toX,
							y: fromY
						};

						var turnBottom = {
							x: turnTop.x,
							y: toY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":toX, "y": toY});

						drawPointsArrow(ctx, pointsArr);
					}
					else if( heightDiff < 0 ) {
						//终点在起点之上
						var turnTop = {
							x: ( fromX - toX ) / 2 + toX,
							y: toY
						};

						var turnBottom = {
							x: turnTop.x,
							y: fromY
						};

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":turnBottom.x, "y": turnBottom.y});
						pointsArr.push({"x":turnTop.x, "y": turnTop.y});
						pointsArr.push({"x":toX, "y": toY});

						drawPointsArrow(ctx, pointsArr);
					}
					else if( heightDiff === 0 ) {
						ctx.moveTo(fromX, fromY);
						ctx.lineTo(toX, toY);

						pointsArr = [];
						pointsArr.push({"x":fromX, "y": fromY});
						pointsArr.push({"x":toX, "y": toY});

						drawPointsArrow(ctx, pointsArr);
					}
				}
				else if( widthDiff >= 0 ) {
					//终点位于起点的右侧

					//终点位于起点之下
					if( heightDiff > 0 ) {
						//判断终点和fromBox低端的距离是否大于30
						if( toY - ( fromBox.position().top + fromBox.height() ) < 30 ) {
							if( toBox ) {

							} else {

								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : fromBox.position().top + fromBox.height() + 30
								};

								var turn3 = {
									x: toX,
									y: turn2.y
								};

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":turn3.x, "y": turn3.y});
								pointsArr.push({"x":toX, "y": toY});

								drawPointsArrow(ctx, pointsArr);
							}
						} else if( toY - ( fromBox.position().top + fromBox.height() ) >= 30 ) {

							if( toBox ){
								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : ( toBox.position().top - ( fromBox.position().top + fromBox.height() ) )/2 + ( fromBox.position().top + fromBox.height() )
								};

								var turn3 = {
									x: toX + 30,
									y: turn2.y
								};

								var turn4 = {
									x: turn3.x,
									y: toY
								};

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":turn3.x, "y": turn3.y});
								pointsArr.push({"x":turn4.x, "y": turn4.y});
								pointsArr.push({"x":toX, "y": toY});

								drawPointsArrow(ctx, pointsArr);
							} else{
								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : fromBox.position().top + fromBox.height() + 30
								};

								var turn3 = {
									x: toX,
									y: turn2.y
								};

								//判断终点与转折点1的横坐标差与纵坐标差间的大小
								if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
									//纵坐标差大于横坐标差

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":turn3.x, "y": turn3.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								} else {

									turn2.x = turn1.x;
									turn2.y = toY;

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								}
							}
						}
					}
					else if(  heightDiff <= 0 ) {
						//判断终点和fromBox顶端端的距离是否大于30
						if( ( fromBox.position().top - toY ) < 30 ) {
							if( toBox ) {

							} else {
								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : fromBox.position().top - 30
								};

								var turn3 = {
									x: toX,
									y: turn2.y
								};

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":turn3.x, "y": turn3.y});
								pointsArr.push({"x":toX, "y": toY});

								drawPointsArrow(ctx, pointsArr );

							}
						} else if( fromBox.position().top - toY >= 30 ) {

							if( toBox ) {
								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : ( fromBox.position().top - ( toBox.position().top + toBox.height() ) )/2 + ( toBox.position().top + toBox.height() )
								};

								var turn3 = {
									x: toX + 30,
									y: turn2.y
								};

								var turn4 = {
									x: turn3.x,
									y: toY
								};

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":turn3.x, "y": turn3.y});
								pointsArr.push({"x":turn4.x, "y": turn4.y});
								pointsArr.push({"x":toX, "y": toY});

								drawPointsArrow(ctx, pointsArr);

							} else {

								var turn1 = {
									x : fromX - 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : fromBox.position().top - 30
								};

								var turn3 = {
									x: toX,
									y: turn2.y
								};

								//判断终点与转折点1的横坐标差与纵坐标差间的大小
								if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
									//纵坐标差大于横坐标差

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":turn3.x, "y": turn3.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								} else {

									turn2.x = turn1.x;
									turn2.y = toY;

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr);
								}
							}
						}
					}
				}
			}
		}
		return pointsArr;
	}
	
	function reDrawArrow(boxObj) {
		var curBoxId = boxObj.attr("id");
		var linContainers = $(".line_box_container");
		var powerCanvas = document.getElementById("power-canvas");
		var canvasCtx = powerCanvas.getContext("2d");
		for( var i = 0; i < linContainers.length; i++ ){
			var curlineContainer = $(linContainers[i]);

			if( curlineContainer.data("lineInfo").fromBoxId == curBoxId || curlineContainer.data("lineInfo").toBoxId == curBoxId ){
				var fromBox = $("#"+curlineContainer.data("lineInfo").fromBoxId);
				var toBox = $("#"+curlineContainer.data("lineInfo").toBoxId);
				var fromIndex = curlineContainer.data("lineInfo").fromIndex;
				var toIndex = curlineContainer.data("lineInfo").toIndex;
				var fromY = ( fromIndex + 1 ) * 30 - 15 + 40 + fromBox.position().top;
				var toY = ( toIndex + 1 ) * 30 - 15 + 40 + toBox.position().top;
				var fromX, toX;

				if( fromBox.find(".column-item").eq(fromIndex+1).css("display") == "none" || toBox.find(".column-item").eq(toIndex+1).css("display") == "none"){
					curlineContainer.css("display","none");
				}else {
					curlineContainer.css("display","block");
				}

				//判断出发点为向左还是向右
				if( toBox.position().left > (fromBox.position().left+fromBox.width()/2) ){
					//向右
					fromX = fromBox.position().left + fromBox.width();
					toX = toBox.position().left
				}else {
					//向左
					fromX = fromBox.position().left;
					toX = toBox.position().left + toBox.width();
				}

				drawArrow( canvasCtx,
					fromX,
					fromY,
					toX,
					toY,
					fromBox,
					toBox,
					curlineContainer,
					fromIndex,
					toIndex
				)
			}
		}
	}

	SummerBox.prototype.switch = function () {
		var $this = this.elem;
		$this.click(function (e) {

			$this.addClass("summer-Box-touch").siblings().removeClass("summer-Box-touch");

			//确定四个点的位置
			rebuildPointPosition($this);

			$(".position-circle").css("display", "block");
		});
	};

	SummerBox.prototype.showMore = function () {
		var $this = this.elem;
		$this.find(".drop-arrow-container").click(function (e) {
			//获取当前box下有多少个字段
			var curBoxFields = $this.find(".summer-column .column-item");
			var curBoxFieldsSize = curBoxFields.length;
			$this.height(40 + ( curBoxFieldsSize * 30 ));
			curBoxFields.each(function () {
				$(this).css("display","block");
			});
			$(this).css("display","none");
			reDrawArrow($this);
		});
	};

	SummerBox.prototype.drawSummerArrow = function () {
		var $this = this.elem;
		var container = $("#container");
		var powerContainer = $("#power-container");
		$this.find(".summer-column .column-item").mouseenter(function (e) {
			$(this).addClass("column-active").siblings().removeClass("column-active");
		});
		$this.find(".summer-column .column-item").mouseleave(function (e) {
			$(this).removeClass("column-active").siblings().removeClass("column-active");
		});
		$this.find(".summer-column .column-item").mousedown(function (e) {
			var $curColumn = $(this);
			var pointsArr = [];
			var oldX = e.clientX - powerContainer[0].offsetLeft + powerContainer[0].scrollLeft;
			var oldY = e.clientY - $(".power-main")[0].offsetTop + powerContainer[0].scrollTop;

			var powerCanvas = document.getElementById("power-canvas");
			var canvasCtx = powerCanvas.getContext("2d");

			var fromX = 0, fromY = 0, newX = 0, newY = 0, toBox = null, toItem = null, fromIndex, toIndex;

			container.unbind("mousemove");
			container.mousemove(function (e) {
				newX = e.clientX - powerContainer[0].offsetLeft + powerContainer[0].scrollLeft;
				newY = e.clientY - $(".power-main")[0].offsetTop + powerContainer[0].scrollTop;
				container.css("cursor","crosshair");
				//判断向左还是向右

				fromY = ( $curColumn.index() + 1 ) * 30 - 15 + 40 + $this.position().top;
				fromIndex = $curColumn.index();
				if( ( newX - ( $this.position().left + $this.width()/2 ) ) > 0  ){
					//向右
					//从鼠标离开column开始划线
					// if( ( newX - $this.position().left - $this.width() ) >= 0 ){
						fromX = $this.position().left + $this.width();

						canvasCtx.clearRect(0, 0, powerCanvas.width, powerCanvas.height);
						if( ($(e.target).hasClass("column-item") || $(e.target).parents(".summer-box").length > 0) ){
							//目标点在column上
							toBox = $(e.target).parents(".summer-box");
							toItem = $($(e.target).parents(".column-item")[0]);
							newX = toBox.position().left;
							newY = ( toItem.index() + 1 ) * 30 - 15 + 40 + toBox.position().top;
							toIndex = toItem.index();
							pointsArr = drawArrow(canvasCtx, fromX, fromY, newX, newY, $this, toBox);
						}else {
							pointsArr = drawArrow(canvasCtx, fromX, fromY, newX, newY, $this);
						}
					// }
				}else {
					//向左
					fromX = $this.position().left;

					canvasCtx.clearRect(0, 0, powerCanvas.width, powerCanvas.height);
					if( ($(e.target).hasClass("column-item") || $(e.target).parents(".summer-box").length > 0) ){
						//目标点在column上
						toBox = $(e.target).parents(".summer-box");
						toItem = $($(e.target).parents(".column-item")[0]);
						newX = toBox.position().left + $this.width();
						newY = ( toItem.index() + 1 ) * 30 - 15 + 40 + toBox.position().top;
						toIndex = toItem.index();
						pointsArr = drawArrow(canvasCtx, fromX, fromY, newX, newY, $this, toBox);
					}else {
						pointsArr = drawArrow(canvasCtx, fromX, fromY, newX, newY, $this);
					}
				}
			});

			container.mouseup(function (e) {
				container.unbind("mousemove");
				container.unbind("mouseup");
				container.css("cursor","default");

				var lineId = getuuid(16,16);

				container.append(
					"<div id='"+ lineId +"' class='line_box_container' style='position: absolute;'>" +
					"<canvas></canvas>" +
					"</div>"
				);

				if( $(e.target).parents(".summer-box").length > 0 || $(e.target).hasClass("summer-box") ){
					var toBox = $($(e.target).parents(".summer-box")[0]);
					if( toBox ){
						canvasCtx.clearRect(0, 0, powerCanvas.width, powerCanvas.height);
						//保存外键
						var fromBoxData = $this.data("boxDetail");
						var toBoxData = toBox.data("boxDetail");
	
						if( toBoxData.sqlFieldes[toItem.index()].fieldKey === "0" ){
							$.message({
								message:'目标字段必须为主键',
								type:'error'
							});
							return;
						}else if( toBoxData.sqlFieldes[toItem.index()].fieldKey === "1" ){
							if( toBoxData.sqlFieldes[toItem.index()].dateType !== fromBoxData.sqlFieldes[$curColumn.index()].dateType ){
								$.message({
									message:'关联字段类型不一致',
									type:'error'
								});
								return;
							}
						}else if( (fromBoxData.sqlShape && fromBoxData.sqlShape.length >= 0) || (toBoxData.sqlShape && toBoxData.sqlShape.length >= 0) ){
							$.message({
								message:'关联表存在分区，分表或分库，无法创建外键！',
								type:'error'
							});
							return;
						}
						drawArrow(canvasCtx, fromX, fromY, newX, newY, $this, toBox, $("#"+lineId), $curColumn.index(), toItem.index());
						//保存外键
						$.ajax({
							url: curContext + '/v1/sqlModel/createForeignkey',
							type:"post",
							data: JSON.stringify({ "mainTable": toBoxData.tableId, "mainField": toBoxData.sqlFieldes[toItem.index()].fieldSqlName, "joinTable": fromBoxData.tableId, "joinField": fromBoxData.sqlFieldes[$curColumn.index()].fieldSqlName}),
							dataType : "json",
							contentType:"application/json",
							success : function (result) {
								if( result.foreignKeyId ) {
									$.message('创建外键成功！');
									$("#"+lineId).data("foreignKey",result);
								} else {
									$.message({
										message:'创建外键失败！',
										type:'error'
									});
								}
							}
						});
					}
				}
				//定制lineContainer的样式
				else {
					$("#"+lineId).remove();
					canvasCtx.clearRect(0, 0, powerCanvas.width, powerCanvas.height);
				}
			});
		});
	};

	SummerBox.prototype.pointTouch = function () {
		$(".position-circle").mousedown(function (e) {
			var e = e || window.event;
			var curPoint = $(this);
			var parentContainer = $(this).parent();
			var powerContainer = $("#power-container");
			var curSummerBox = $(".summer-Box-touch");

			var oldX = e.clientX - powerContainer[0].offsetLeft + powerContainer[0].scrollLeft;
			var oldY = e.clientY - $(".power-main")[0].offsetTop + powerContainer[0].scrollTop;
			var oldHeight = curSummerBox.height();
			var oldWidth = curSummerBox.width();
			var oldTop = curSummerBox.position().top;

			//获取当前box下有多少个字段
			var curBoxFields = curSummerBox.find(".summer-column .column-item");
			var curBoxFieldsSize = curBoxFields.length;

			parentContainer.unbind("mousemove");
			parentContainer.mousemove(function (e) {
				var newX = e.clientX - powerContainer[0].offsetLeft + powerContainer[0].scrollLeft;
				var newY = e.clientY - $(".power-main")[0].offsetTop + powerContainer[0].scrollTop;

				var curBoxHeight = curSummerBox.height();
				var couldBeInNum = parseInt((curBoxHeight-40)/30);
				var shouldHideNum = curBoxFieldsSize - couldBeInNum;

				//该隐藏的隐藏掉
				for( var i = 0; i < shouldHideNum; i++ ){
					$(curBoxFields[(curBoxFieldsSize-i-1)]).css("display","none");
				}

				//该显示的显示
				for( var j=0; j<couldBeInNum; j++ ){
					$(curBoxFields[j]).css("display","block");
				}

				//控制显示更多的下滑按钮
				if( couldBeInNum < curBoxFieldsSize ){
					$(curBoxFields[(couldBeInNum-1)]).css("display","none");
					curSummerBox.find(".drop-arrow-container").css("display","block");
				} else {
					curSummerBox.find(".drop-arrow-container").css("display","none");
				}

				if( curPoint.hasClass("circle-top") ){
					//上
					curSummerBox.css("top", ( oldTop - ( oldY - newY ) ) + "px" );
					curSummerBox.css("height", ( oldHeight + ( oldY - newY ) ) + "px" );
				} else if( curPoint.hasClass("circle-right") ){
					//右
					curSummerBox.css("left", curSummerBox.position().left  + "px" );
					curSummerBox.css("width", ( newX - curSummerBox.position().left ) + "px" );
				} else if( curPoint.hasClass("circle-bottom") ){
					//下
					curSummerBox.css("top", curSummerBox.position().top  + "px" );
					curSummerBox.css("height", ( newY - curSummerBox.position().top ) + "px" );
				} else if( curPoint.hasClass("circle-left") ){
					//左
					curSummerBox.css("left", newX  + "px" );
					curSummerBox.css("width", ( oldWidth + ( oldX - newX ) ) + "px" );
				} else if( curPoint.hasClass("circle-top-right") ){
					//上右
					curSummerBox.css("top", ( oldTop - ( oldY - newY ) ) + "px" );
					curSummerBox.css("left", curSummerBox.position().left  + "px" );
					curSummerBox.css("height", ( oldHeight + ( oldY - newY ) ) + "px" );
					curSummerBox.css("width", ( newX - curSummerBox.position().left ) + "px" );
				} else if( curPoint.hasClass("circle-top-left") ){
					//上左
					curSummerBox.css("top", ( oldTop - ( oldY - newY ) ) + "px" );
					curSummerBox.css("height", ( oldHeight + ( oldY - newY ) ) + "px" );
					curSummerBox.css("left", newX  + "px" );
					curSummerBox.css("width", ( oldWidth + ( oldX - newX ) ) + "px" );
				} else if( curPoint.hasClass("circle-bottom-right") ){
					//下右
					curSummerBox.css("top", curSummerBox.position().top  + "px" );
					curSummerBox.css("height", ( newY - curSummerBox.position().top ) + "px" );
					curSummerBox.css("left", curSummerBox.position().left  + "px" );
					curSummerBox.css("width", ( newX - curSummerBox.position().left ) + "px" );
				} else if( curPoint.hasClass("circle-bottom-left") ){
					//下左
					curSummerBox.css("top", curSummerBox.position().top  + "px" );
					curSummerBox.css("height", ( newY - curSummerBox.position().top ) + "px" );
					curSummerBox.css("left", newX  + "px" );
					curSummerBox.css("width", ( oldWidth + ( oldX - newX ) ) + "px" );
				}

				//四个点的位置也随之移动
				rebuildPointPosition(curSummerBox);

				//显示或隐藏外键
				reDrawArrow(curSummerBox);
			});

			parentContainer.mouseup(function (e) {
				parentContainer.unbind("mousemove");
			});
		});
	};

	SummerBox.prototype.titleTouch = function () {
		var $this = this.elem;
		$this.find(".summer-box-title").mousedown(function (e) {
			$(this).css("cursor","move");

			var mouseInnerLeft = e.clientX - $this.position().left + $("#power-container").scrollLeft();
			var mouseInnerTop = e.clientY - $this.position().top + $("#power-container").scrollTop();

			$("#power-container").unbind("mousemove");
			$("#power-container").mousemove(function (e) {
				var container = $("#container");
				var powerCanvas = document.getElementById("power-canvas");
				var nl = e.clientX + $("#power-container").scrollLeft() - mouseInnerLeft;
				var nt = e.clientY + $("#power-container").scrollTop() - mouseInnerTop;

				$this.css("top",nt + 'px');
				$this.css("left",nl + 'px');

				//container的宽高
				var containerHeight = container.height();
				var containerWidth = container.width();

				if( ( nt + $this.height() ) > containerHeight ) {
					container.height( containerHeight + $this.height() );
					powerCanvas.height = containerHeight + $this.height();
				}

				if( ( nl + $this.width() ) > containerWidth ) {
					container.width( containerWidth + $this.width() );
					powerCanvas.width = containerWidth + $this.width();
				}
				//确定四个点的位置
				rebuildPointPosition($this);

				//重新划线
				reDrawArrow($this);
			});
		});
		$this.find(".summer-box-title").mouseup(function (e) {
			$("#power-container").unbind("mousemove");
			$(this).css("cursor","default");
			saveXY();
		});
	};

	SummerBox.prototype.doubleClick = function () {
		var $this = this.elem;
		$this.dblclick(function () {
			showSummerBoxDetail($this);
			
			$("#newTableModal").find(".modal-footer").empty();
			//检查是否有别人在使用
			if( isHaveOtherUser ){
				//没人用
				$("#newTableModal").find(".modal-footer").append(
					'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
					'<button type="button" class="btn btn-primary table-submit">提交</button>'
				);
			}else {
				//有人用
				$("#newTableModal").find(".modal-footer").empty();
			}
			
			if( $(".table-submit").length <= 0 ){
				return;
			}

			$("#table-info-form").data('bootstrapValidator').destroy();
			$('#table-info-form').data('bootstrapValidator', null);

			tableFormValidator();

			$(".table-submit").unbind("click");
			$(".table-submit").click(function (event) {
				
				//校验表基本信息是否通过
				var tableValidator = $("#table-info-form").data('bootstrapValidator');
				tableValidator.validate();
				if(  !tableValidator.isValid() ){
					canvasTab.open($("#basic"));
					tableFormValidator();
					return;
				}

				//如是分表则进行分表校验
				if( $("#shapeType-option").val() === "2" ){
					//分表
					var metersValidator = $("#sub_form").data('bootstrapValidator');
					metersValidator.validate();
					if( !metersValidator.isValid() ){
						canvasTab.open($("#option"));
						meterValidator();
						return;
					}
				}else if( $("#shapeType-option").val() === "3" ){
					//分库校验
					var treasuryValidate = $("#sub_form").data('bootstrapValidator');
					treasuryValidate.validate();
					if( !treasuryValidate.isValid() ){
						canvasTab.open($("#option"));
						treasuryValidator();
						return;
					}

					//对字库进行校验
					var childForm = $("#dataInputs").find("form");
					if( childForm && childForm.length > 0 ){
						for( var c = 0; c < childForm.length; c++ ){
							var curFormValidate = $(childForm[c]).data('bootstrapValidator');
							curFormValidate.validate();
							if( !curFormValidate.isValid() ){
								canvasTab.open($("#option"));
								childFormValidator($(childForm[c]));
								return;
							}
						}
					}
				} else if( $("#shapeType-option").val() === "1" ){
					//分区校验
					var areasValidator = $("#sub_form").data('bootstrapValidator');
					areasValidator.validate();
					if( !areasValidator.isValid() ){
						canvasTab.open($("#option"));
						areaValidator();
						return;
					}
				}

				var curBox = $this;
				var curBoxData = curBox.data("boxDetail");
				if( curBoxData ){
					//编辑
					var shapeTypeSelect = $("#shapeType-option"); //操作类型select
					var subArea = $(".sub-area"); //分区
					if( shapeTypeSelect.val() === "1" ){
						//分区
						var shapeState = subArea.find("input[name='state']").eq(0).is(":checked") ? "0" : "1";
						var partitionValue = subArea.find("textarea[name='partitionValue']").val();
						if( curBoxData["sqlShape[0].state"] !== shapeState || curBoxData["sqlShape[0].partitionValue"] !== partitionValue ){
							$("#confirmCloseModal").modal("show");
							$(".confirm-submit").unbind("click");
							$(".confirm-submit").click(function(){
								$("#confirmCloseModal").modal("hide");
								dataModalSave(curBox, 'edit');
							});
						}
					}else {
						dataModalSave(curBox, 'edit');
					}
				}else {
					//保存
					dataModalSave(curBox, 'save');
				}
			});
		});
	};
	
	$.fn.extend({
		summerBox: function () {
			this.each(function () {
				return new SummerBox($(this));
			});
			return this;
		}
	});
 
	window.SummerBox = SummerBox;

	// $(document).on('click.summer-box', '[data-toggle="summer-box"]')

}(jQuery);

	$(document).click(function (e) {
		var event = e || window.event;
	});

