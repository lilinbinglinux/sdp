function drawPointsArrow( ctx, points, theta, headlen, width, color, shadow ){
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
	if( shadow ){
		ctx.shadowBlur=10;
		ctx.shadowColor="red";
	}else {
		ctx.shadowBlur=0;
	}
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


function drawArrow( ctx, fromX, fromY, toX, toY, fromBox, toBox, lineContainer, fromIndex, toIndex, shadow ) {
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else {
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else {
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

									drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
								}else{
									drawPointsArrow(ctx, arr, 30, 10, 2, '#000', shadow);
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

									drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
								}else{
									drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}
					else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
					}else{
						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
							}else{
								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(lineCanvas, innerPointsArr, 30, 10, 2, '#000', shadow);
							}else{
								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

					drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

					drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
				}
				else if( heightDiff === 0 ){

					pointsArr = [];
					pointsArr.push({"x":fromX, "y": fromY});
					pointsArr.push({"x":toX, "y": toY});

					drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(ctx, arr, 30, 10, 2, '#000', shadow);
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

									drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
								} else {
									turn2.x = turn1.x;
									turn2.y = toY;

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

									drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
								} else {

									turn2.x = turn1.x;
									turn2.y = toY;

									pointsArr = [];
									pointsArr.push({"x":fromX, "y": fromY});
									pointsArr.push({"x":turn1.x, "y": turn1.y});
									pointsArr.push({"x":turn2.x, "y": turn2.y});
									pointsArr.push({"x":toX, "y": toY});

									drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

						drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

					drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

					drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
				}
				else if( heightDiff === 0 ) {
					ctx.moveTo(fromX, fromY);
					ctx.lineTo(toX, toY);

					pointsArr = [];
					pointsArr.push({"x":fromX, "y": fromY});
					pointsArr.push({"x":toX, "y": toY});

					drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

							drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

							drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
							} else {

								turn2.x = turn1.x;
								turn2.y = toY;

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":toX, "y": toY});

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
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

							drawPointsArrow(ctx, pointsArr , 30, 10, 2, '#000', shadow);

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

							drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);

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

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
							} else {

								turn2.x = turn1.x;
								turn2.y = toY;

								pointsArr = [];
								pointsArr.push({"x":fromX, "y": fromY});
								pointsArr.push({"x":turn1.x, "y": turn1.y});
								pointsArr.push({"x":turn2.x, "y": turn2.y});
								pointsArr.push({"x":toX, "y": toY});

								drawPointsArrow(ctx, pointsArr, 30, 10, 2, '#000', shadow);
							}
						}
					}
				}
			}
		}
	}
	return pointsArr;
}