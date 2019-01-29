function drawArrow(ctx, fromX, fromY, toX, toY,theta,headlen,width,color,fromBox,toBox,curLineDetail,curLineIndex) {

	//起点和终点的高粗差
	var heightDiff = toY - fromY; //终点和起点的高度差
	var widthDiff = toX - fromX; //终点和起点的横坐标差

  //终点位于起点的右侧
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

		    var arr = [];
		    arr.push({"x":fromX, "y": fromY});
		    arr.push({"x":turnTop.x, "y": turnTop.y});
		    arr.push({"x":turnBottom.x, "y": turnBottom.y});
		    arr.push({"x":toX, "y": toY});

		    if( curLineDetail ){
			    curLineDetail.turnPoints.push({"x":turnTop.x, "y":turnTop.y});
			    curLineDetail.turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
			    linesArray.push(curLineDetail);
		    }

				if( curLineIndex != null ){
					linesArray[curLineIndex].turnPoints.push({"x":turnTop.x, "y":turnTop.y});
					linesArray[curLineIndex].turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
				}

	      drawPointsArrow(ctx, arr, theta, headlen, width, color);
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

	    var arr = [];
	    arr.push({"x":fromX, "y": fromY});
	    arr.push({"x":turnBottom.x, "y": turnBottom.y});
	    arr.push({"x":turnTop.x, "y": turnTop.y});
	    arr.push({"x":toX, "y": toY});

	    if( curLineDetail ){
		    curLineDetail.turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
		    curLineDetail.turnPoints.push({"x":turnTop.x, "y":turnTop.y});
		    linesArray.push(curLineDetail);
	    }
	    if( curLineIndex != null ){
		    linesArray[curLineIndex].turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
		    linesArray[curLineIndex].turnPoints.push({"x":turnTop.x, "y":turnTop.y});
	    }

	    drawPointsArrow(ctx, arr, theta, headlen, width, color);
    }
    else if( heightDiff == 0 ){

	    var arr = [];
	    arr.push({"x":fromX, "y": fromY});
	    arr.push({"x":toX, "y": toY});

	    if( curLineDetail ){
		    linesArray.push(curLineDetail);
	    }

	    drawPointsArrow(ctx, arr, theta, headlen, width, color);
    }
  }
  else if( widthDiff <= 0 ) {
      //终点位于起点左侧

      //终点位于起点之下
      if( heightDiff > 0 ) {

        /*
        * 增加四个转折点
        * */
        // var centerY = ( toBox.offsetTop - ( fromBox.offsetTop + $(fromBox).height() ) )/2 + ( fromBox.offsetTop + $(fromBox).height() );


        //判断终点与转折点1的横坐标差与纵坐标差间的大小
        // if( Math.abs( toX - turn1.x ) < Math.abs( toY - turn1.y ) ) {
					if( fromBox ) {
						//判断终点和fromBox低端的距离是否大于30
						if( toY - ( fromBox.offsetTop + $(fromBox).height() ) < 30 ) {
							if( toBox ) {


							}else {
								var turn1 = {
									x : fromX + 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : fromBox.offsetTop + $(fromBox).height() + 30
								};

								var turn3 = {
									x: toX,
									y: turn2.y
								};

								var arr = [];
								arr.push({"x":fromX, "y": fromY});
								arr.push({"x":turn1.x, "y": turn1.y});
								arr.push({"x":turn2.x, "y": turn2.y});
								arr.push({"x":turn3.x, "y": turn3.y});
								arr.push({"x":toX, "y": toY});

								if( curLineDetail ){
									curLineDetail.turnPoints.push({"x":turn1.x, "y":turn2.y});
									curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
									curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
									linesArray.push(curLineDetail);
								}
								if( curLineIndex != null ){
									linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn3.x, "y":turn3.y});
								}
								drawPointsArrow(ctx, arr, theta, headlen, width, color);
							}

						} else if( toY - ( fromBox.offsetTop + $(fromBox).height() ) >= 30 ) {

							if( toBox ){
								var turn1 = {
									x : fromX + 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : ( toBox.offsetTop - ( fromBox.offsetTop + $(fromBox).height() ) )/2 + ( fromBox.offsetTop + $(fromBox).height() )
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

								if( curLineDetail ){
									curLineDetail.turnPoints.push({"x":turn1.x, "y":turn2.y});
									curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
									curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
									curLineDetail.turnPoints.push({"x":turn4.x, "y":turn4.y});
									linesArray.push(curLineDetail);
								}
								if( curLineIndex != null ){
									linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn3.x, "y":turn3.y});
									linesArray[curLineIndex].turnPoints.push({"x":turn4.x, "y":turn4.y});
								}
								drawPointsArrow(ctx, arr, theta, headlen, width, color);
							}
							else {
								var turn1 = {
									x : fromX + 30,
									y : fromY
								};

								var turn2 = {
									x : turn1.x,
									y : fromBox.offsetTop + $(fromBox).height() + 30
								};

								var turn3 = {
									x: toX,
									y: turn2.y
								};

								//判断终点与转折点1的横坐标差与纵坐标差间的大小
								if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
									//纵坐标差大于横坐标差

									var arr = [];
									arr.push({"x":fromX, "y": fromY});
									arr.push({"x":turn1.x, "y": turn1.y});
									arr.push({"x":turn2.x, "y": turn2.y});
									arr.push({"x":turn3.x, "y": turn3.y});
									arr.push({"x":toX, "y": toY});

									if( curLineDetail ){
										curLineDetail.turnPoints.push({"x":turn1.x, "y":turn2.y});
										curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
										curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
										linesArray.push(curLineDetail);
									}
									if( curLineIndex != null ){
										linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn3.x, "y":turn3.y});
									}

									drawPointsArrow(ctx, arr, theta, headlen, width, color);
								} else {
									turn2.x = turn1.x;
									turn2.y = toY;

									var arr = [];
									arr.push({"x":fromX, "y": fromY});
									arr.push({"x":turn1.x, "y": turn1.y});
									arr.push({"x":turn2.x, "y": turn2.y});
									arr.push({"x":toX, "y": toY});

									if( curLineDetail ){
										curLineDetail.turnPoints.push({"x":turn1.x, "y":turn2.y});
										curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
										linesArray.push(curLineDetail);
									}
									if( curLineIndex != null ){
										linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
										linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
									}

									drawPointsArrow(ctx, arr, theta, headlen, width, color);
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

						var arr = [];
						arr.push({"x":fromX, "y": fromY});
						arr.push({"x":turnBottom.x, "y": turnBottom.y});
						arr.push({"x":turnTop.x, "y": turnTop.y});
						arr.push({"x":toX, "y": toY});

						if( curLineDetail ){
							curLineDetail.turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
							curLineDetail.turnPoints.push({"x":turnTop.x, "y":turnTop.y});
							linesArray.push(curLineDetail);
						}
						if( curLineIndex != null ){
							linesArray[curLineIndex].turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
							linesArray[curLineIndex].turnPoints.push({"x":turnTop.x, "y":turnTop.y});
						}

						drawPointsArrow(ctx, arr, theta, headlen, width, color);
					}


      } else if( heightDiff <= 0 ) {
				//终点位于起点之上

	      if( fromBox ) {
					//判断终点和fromBox顶端端的距离是否大于30
		      if( ( fromBox.offsetTop - toY ) < 30 ) {
			      if( toBox ) {


			      }else {
				      var turn1 = {
					      x : fromX + 30,
					      y : fromY
				      };

				      var turn2 = {
					      x : turn1.x,
					      y : fromBox.offsetTop - 30
				      };

				      var turn3 = {
					      x: toX,
					      y: turn2.y
				      };

				      var arr = [];
				      arr.push({"x":fromX, "y": fromY});
				      arr.push({"x":turn1.x, "y": turn1.y});
				      arr.push({"x":turn2.x, "y": turn2.y});
				      arr.push({"x":turn3.x, "y": turn3.y});
				      arr.push({"x":toX, "y": toY});

				      if( curLineDetail ){
					      curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
					      curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
					      curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
					      linesArray.push(curLineDetail);
				      }
				      if( curLineIndex != null ){
					      linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
					      linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
					      linesArray[curLineIndex].turnPoints.push({"x":turn3.x, "y":turn3.y});
				      }

				      drawPointsArrow(ctx, arr, theta, headlen, width, color);
			      }
		      } else if( ( fromBox.offsetTop - toY ) >= 30 ) {
			      if( toBox ) {
				      var turn1 = {
					      x : fromX + 30,
					      y : fromY
				      };

				      var turn2 = {
					      x : turn1.x,
					      y : ( fromBox.offsetTop - ( toBox.offsetTop + $(toBox).height() ) )/2 + ( toBox.offsetTop + $(toBox).height() )
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

				      if( curLineDetail ){
					      curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
					      curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
					      curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
					      curLineDetail.turnPoints.push({"x":turn4.x, "y":turn4.y});
					      linesArray.push(curLineDetail);
				      }
				      if( curLineIndex != null ){
					      linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
					      linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
					      linesArray[curLineIndex].turnPoints.push({"x":turn3.x, "y":turn3.y});
					      linesArray[curLineIndex].turnPoints.push({"x":turn4.x, "y":turn4.y});
				      }

				      drawPointsArrow(ctx, arr, theta, headlen, width, color);
			      }
			      else {

				      var turn1 = {
					      x : fromX + 30,
					      y : fromY
				      }

				      var turn2 = {
					      x : turn1.x,
					      y : fromBox.offsetTop - 30
				      }

				      var turn3 = {
					      x: toX,
					      y: turn2.y
				      };

				      //判断终点与转折点1的横坐标差与纵坐标差间的大小
				      if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
					      //纵坐标差大于横坐标差

					      var arr = [];
					      arr.push({"x":fromX, "y": fromY});
					      arr.push({"x":turn1.x, "y": turn1.y});
					      arr.push({"x":turn2.x, "y": turn2.y});
					      arr.push({"x":turn3.x, "y": turn3.y});
					      arr.push({"x":toX, "y": toY});

					      if( curLineDetail ){
						      curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
						      curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
						      curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
						      linesArray.push(curLineDetail);
					      }
					      if( curLineIndex != null ){
						      linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
						      linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
						      linesArray[curLineIndex].turnPoints.push({"x":turn3.x, "y":turn3.y});
					      }

					      drawPointsArrow(ctx, arr, theta, headlen, width, color);
				      } else {

					      turn2.x = turn1.x;
					      turn2.y = toY;

					      var arr = [];
					      arr.push({"x":fromX, "y": fromY});
					      arr.push({"x":turn1.x, "y": turn1.y});
					      arr.push({"x":turn2.x, "y": turn2.y});
					      arr.push({"x":toX, "y": toY});

					      if( curLineDetail ){
						      curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
						      curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
						      linesArray.push(curLineDetail);
					      }
					      if( curLineIndex != null ){
						      linesArray[curLineIndex].turnPoints.push({"x":turn1.x, "y":turn1.y});
						      linesArray[curLineIndex].turnPoints.push({"x":turn2.x, "y":turn2.y});
					      }
					      drawPointsArrow(ctx, arr, theta, headlen, width, color);
				      }
			      }
		      }
	      }else {

	      	var turnTop = {
			      x: (fromX - toX)/2 + toX,
			      y: fromY
		      };
		      var turnBottom = {
			      x: turnTop.x,
			      y: toY
		      };

		      var arr = [];
		      arr.push({"x":fromX, "y": fromY});
		      arr.push({"x":turnTop.x, "y": turnTop.y});
		      arr.push({"x":turnBottom.x, "y": turnBottom.y});
		      arr.push({"x":toX, "y": toY});

		      if( curLineDetail ){
			      curLineDetail.turnPoints.push({"x":turnTop.x, "y":turnTop.y});
			      curLineDetail.turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
			      linesArray.push(curLineDetail);
		      }
		      if( curLineIndex != null ){
			      linesArray[curLineIndex].turnPoints.push({"x":turnTop.x, "y":turnTop.y});
			      linesArray[curLineIndex].turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
		      }
		      drawPointsArrow(ctx, arr, theta, headlen, width, color);
	      }
      }
    }
}

function drawPointsArrow( ctx, points, theta, headlen, width, color ){
	theta = typeof(theta) != 'undefined' ? theta : 30;
	headlen = typeof(theta) != 'undefined' ? headlen : 10;
	width = typeof(width) != 'undefined' ? width : 1;
	color = typeof(color) != 'color' ? color : '#000';

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

function drawLeftArrow(ctx, fromX, fromY, toX, toY,theta,headlen,width,color,fromBox,toBox,curLineDetail) {

	//起点和终点的高粗差
	var heightDiff = toY - fromY; //终点和起点的高度差
	var widthDiff = toX - fromX; //终点和起点的横坐标差

	//终点位于起点的左侧
	if( widthDiff < 0 ) {
		//终点位于起点左侧

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

			var arr = [];
			arr.push({"x":fromX, "y": fromY});
			arr.push({"x":turnTop.x, "y": turnTop.y});
			arr.push({"x":turnBottom.x, "y": turnBottom.y});
			arr.push({"x":toX, "y": toY});

			//保存线的信息
			if( curLineDetail ) {
				curLineDetail.turnPoints.push({"x":turnTop.x, "y":turnTop.y});
				curLineDetail.turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
				linesArray.push(curLineDetail);
			}
			drawPointsArrow(ctx, arr, theta, headlen, width, color);
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

			var arr = [];
			arr.push({"x":fromX, "y": fromY});
			arr.push({"x":turnBottom.x, "y": turnBottom.y});
			arr.push({"x":turnTop.x, "y": turnTop.y});
			arr.push({"x":toX, "y": toY});

			//保存线的信息
			if( curLineDetail ) {
				curLineDetail.turnPoints.push({"x":turnBottom.x, "y":turnBottom.y});
				curLineDetail.turnPoints.push({"x":turnTop.x, "y":turnTop.y});
				linesArray.push(curLineDetail);
			}
			drawPointsArrow(ctx, arr, theta, headlen, width, color);
			//水平向右
		}
		else if( heightDiff == 0 ) {
			ctx.moveTo(fromX, fromY);
			ctx.lineTo(toX, toY);

			//保存线的信息
			if( curLineDetail ) {
				linesArray.push(curLineDetail);
			}

			var arr = [];
			arr.push({"x":fromX, "y": fromY});
			arr.push({"x":toX, "y": toY});

			drawPointsArrow(ctx, arr, theta, headlen, width, color);
		}
	}
	else if( widthDiff >= 0 ) {
		//终点位于起点的右侧

		//终点位于起点之下
		if( heightDiff > 0 ) {
			//判断终点和fromBox低端的距离是否大于30
			if( toY - ( fromBox.offsetTop + $(fromBox).height() ) < 30 ) {
				if( toBox ) {

				} else {

					var turn1 = {
						x : fromX - 30,
						y : fromY
					};

					var turn2 = {
						x : turn1.x,
						y : fromBox.offsetTop + $(fromBox).height() + 30
					};

					var turn3 = {
						x: toX,
						y: turn2.y
					};

					var arr = [];
					arr.push({"x":fromX, "y": fromY});
					arr.push({"x":turn1.x, "y": turn1.y});
					arr.push({"x":turn2.x, "y": turn2.y});
					arr.push({"x":turn3.x, "y": turn3.y});
					arr.push({"x":toX, "y": toY});

					//保存线的信息
					if( curLineDetail ) {
						curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
						curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
						curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
						linesArray.push(curLineDetail);
					}

					drawPointsArrow(ctx, arr, theta, headlen, width, color);
				}
			} else if( toY - ( fromBox.offsetTop + $(fromBox).height() ) >= 30 ) {

				if( toBox ){
					var turn1 = {
						x : fromX - 30,
						y : fromY
					};

					var turn2 = {
						x : turn1.x,
						y : ( toBox.offsetTop - ( fromBox.offsetTop + $(fromBox).height() ) )/2 + ( fromBox.offsetTop + $(fromBox).height() )
					};

					var turn3 = {
						x: toX + 30,
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

					//保存线的信息
					if( curLineDetail ) {
						curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
						curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
						curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
						curLineDetail.turnPoints.push({"x":turn4.x, "y":turn4.y});
						linesArray.push(curLineDetail);
					}
					drawPointsArrow(ctx, arr, theta, headlen, width, color);
				} else{
					var turn1 = {
						x : fromX - 30,
						y : fromY
					};

					var turn2 = {
						x : turn1.x,
						y : fromBox.offsetTop + $(fromBox).height() + 30
					};

					var turn3 = {
						x: toX,
						y: turn2.y
					};

					//判断终点与转折点1的横坐标差与纵坐标差间的大小
					if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
						//纵坐标差大于横坐标差

						var arr = [];
						arr.push({"x":fromX, "y": fromY});
						arr.push({"x":turn1.x, "y": turn1.y});
						arr.push({"x":turn2.x, "y": turn2.y});
						arr.push({"x":turn3.x, "y": turn3.y});
						arr.push({"x":toX, "y": toY});

						//保存线的信息
						if( curLineDetail ) {
							curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
							curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
							curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
							linesArray.push(curLineDetail);
						}
						drawPointsArrow(ctx, arr, theta, headlen, width, color);
					} else {

						turn2.x = turn1.x;
						turn2.y = toY;

						var arr = [];
						arr.push({"x":fromX, "y": fromY});
						arr.push({"x":turn1.x, "y": turn1.y});
						arr.push({"x":turn2.x, "y": turn2.y});
						arr.push({"x":toX, "y": toY});

						//保存线的信息
						if( curLineDetail ) {
							curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
							curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
							linesArray.push(curLineDetail);
						}
						drawPointsArrow(ctx, arr, theta, headlen, width, color);
					}
				}
			}
		}
		else if(  heightDiff <= 0 ) {
			//判断终点和fromBox顶端端的距离是否大于30
			if( ( fromBox.offsetTop - toY ) < 30 ) {
				if( toBox ) {

				} else {
					var turn1 = {
						x : fromX - 30,
						y : fromY
					};

					var turn2 = {
						x : turn1.x,
						y : fromBox.offsetTop - 30
					};

					var turn3 = {
						x: toX,
						y: turn2.y
					};

					var arr = [];
					arr.push({"x":fromX, "y": fromY});
					arr.push({"x":turn1.x, "y": turn1.y});
					arr.push({"x":turn2.x, "y": turn2.y});
					arr.push({"x":turn3.x, "y": turn3.y});
					arr.push({"x":toX, "y": toY});

					//保存线的信息
					if( curLineDetail ) {
						curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
						curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
						curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
						linesArray.push(curLineDetail);
					}
					drawPointsArrow(ctx, arr, theta, headlen, width, color);

				}
			} else if( fromBox.offsetTop - toY >= 30 ) {

				if( toBox ) {
					var turn1 = {
						x : fromX - 30,
						y : fromY
					};

					var turn2 = {
						x : turn1.x,
						y : ( fromBox.offsetTop - ( toBox.offsetTop + $(toBox).height() ) )/2 + ( toBox.offsetTop + $(toBox).height() )
					};

					var turn3 = {
						x: toX + 30,
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

					//保存线的信息
					if( curLineDetail ) {
						curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
						curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
						curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
						curLineDetail.turnPoints.push({"x":turn4.x, "y":turn4.y});
						linesArray.push(curLineDetail);
					}
					drawPointsArrow(ctx, arr, theta, headlen, width, color);

				} else {

					var turn1 = {
						x : fromX - 30,
						y : fromY
					};

					var turn2 = {
						x : turn1.x,
						y : fromBox.offsetTop - 30
					};

					var turn3 = {
						x: toX,
						y: turn2.y
					};

					//判断终点与转折点1的横坐标差与纵坐标差间的大小
					if( Math.abs( turn2.y - turn1.y ) - Math.abs( turn3.x - turn2.x ) > 0  ) {
						//纵坐标差大于横坐标差

						var arr = [];
						arr.push({"x":fromX, "y": fromY});
						arr.push({"x":turn1.x, "y": turn1.y});
						arr.push({"x":turn2.x, "y": turn2.y});
						arr.push({"x":turn3.x, "y": turn3.y});
						arr.push({"x":toX, "y": toY});

						//保存线的信息
						if( curLineDetail ) {
							curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
							curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
							curLineDetail.turnPoints.push({"x":turn3.x, "y":turn3.y});
							linesArray.push(curLineDetail);
						}
						drawPointsArrow(ctx, arr, theta, headlen, width, color);
					} else {

						turn2.x = turn1.x;
						turn2.y = toY;

						var arr = [];
						arr.push({"x":fromX, "y": fromY});
						arr.push({"x":turn1.x, "y": turn1.y});
						arr.push({"x":turn2.x, "y": turn2.y});
						arr.push({"x":toX, "y": toY});

						//保存线的信息
						if( curLineDetail ) {
							curLineDetail.turnPoints.push({"x":turn1.x, "y":turn1.y});
							curLineDetail.turnPoints.push({"x":turn2.x, "y":turn2.y});
							linesArray.push(curLineDetail);
						}
						drawPointsArrow(ctx, arr, theta, headlen, width, color);
					}
				}
			}
		}
	}
}

function drawSameRightArrow( ctx, fromX, fromY, toX, toY,theta,headlen,width,color,fromBox,toBox ) {
	theta = typeof(theta) != 'undefined' ? theta : 30;
	headlen = typeof(theta) != 'undefined' ? headlen : 10;
	width = typeof(width) != 'undefined' ? width : 1;
	color = typeof(color) != 'color' ? color : '#000';

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


	ctx.save();
	ctx.beginPath();

	//起点和终点的高粗差
	var heightDiff = toY - fromY; //终点和起点的高度差
	var widthDiff = toX - fromX; //终点和起点的横坐标差

	if( widthDiff > 0 ) {

		if( heightDiff > 0 ) {
			//之上
			var turn1 = {
				x: toX + 30,
				y: fromY
			};
			var turn2 = {
				x: turn1.x,
				y:toY
			};

			ctx.moveTo(fromX, fromY);
			ctx.lineTo(turn1.x, turn1.y);

			ctx.moveTo(turn1.x, turn1.y);
			ctx.lineTo(turn2.x, turn2.y);

			ctx.moveTo(turn2.x, turn2.y);
			ctx.lineTo(toX, toY);

			//此时终点横坐标与第二个转折点的横坐标一直，故箭头向右
			angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
			angle1 = (angle + theta) * Math.PI / 180;
			angle2 = (angle - theta) * Math.PI / 180;
			topX = headlen * Math.cos(angle1);
			topY = headlen * Math.sin(angle1);
			botX = headlen * Math.cos(angle2);
			botY = headlen * Math.sin(angle2);

			arrowX = fromX - topX;
			arrowY = fromY - topY;
			ctx.moveTo(arrowX, arrowY);

			arrowX = toX + topX;
			arrowY = toY + topY;
			ctx.moveTo(arrowX, arrowY);
			ctx.lineTo(toX, toY);
			arrowX = toX + botX;
			arrowY = toY + botY;
			ctx.lineTo(arrowX, arrowY);

		}else if( heightDiff <= 0 ) {
			//之下

			var turn1 = {
				x: toX + 30,
				y: fromY
			};
			var turn2 = {
				x: turn1.x,
				y:toY
			};

			ctx.moveTo(fromX, fromY);
			ctx.lineTo(turn1.x, turn1.y);

			ctx.moveTo(turn1.x, turn1.y);
			ctx.lineTo(turn2.x, turn2.y);

			ctx.moveTo(turn2.x, turn2.y);
			ctx.lineTo(toX, toY);

			//此时终点横坐标与第二个转折点的横坐标一直，故箭头向右
			angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
			angle1 = (angle + theta) * Math.PI / 180;
			angle2 = (angle - theta) * Math.PI / 180;
			topX = headlen * Math.cos(angle1);
			topY = headlen * Math.sin(angle1);
			botX = headlen * Math.cos(angle2);
			botY = headlen * Math.sin(angle2);

			arrowX = fromX - topX;
			arrowY = fromY - topY;
			ctx.moveTo(arrowX, arrowY);

			arrowX = toX + topX;
			arrowY = toY + topY;
			ctx.moveTo(arrowX, arrowY);
			ctx.lineTo(toX, toY);
			arrowX = toX + botX;
			arrowY = toY + botY;
			ctx.lineTo(arrowX, arrowY);
		}
	} else if( widthDiff <= 0 ) {

		if( heightDiff > 0 ) {
			//之下
			var turn1 = {
				x: fromX + 30,
				y: fromY
			};
			var turn2 = {
				x: turn1.x,
				y: toY
			};

			ctx.moveTo(fromX, fromY);
			ctx.lineTo(turn1.x, turn1.y);

			ctx.moveTo(turn1.x, turn1.y);
			ctx.lineTo(turn2.x, turn2.y);

			ctx.moveTo(turn2.x, turn2.y);
			ctx.lineTo(toX, toY);

			//此时终点横坐标与第二个转折点的横坐标一直，故箭头向右
			angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
			angle1 = (angle + theta) * Math.PI / 180;
			angle2 = (angle - theta) * Math.PI / 180;
			topX = headlen * Math.cos(angle1);
			topY = headlen * Math.sin(angle1);
			botX = headlen * Math.cos(angle2);
			botY = headlen * Math.sin(angle2);

			arrowX = fromX - topX;
			arrowY = fromY - topY;
			ctx.moveTo(arrowX, arrowY);

			arrowX = toX + topX;
			arrowY = toY + topY;
			ctx.moveTo(arrowX, arrowY);
			ctx.lineTo(toX, toY);
			arrowX = toX + botX;
			arrowY = toY + botY;
			ctx.lineTo(arrowX, arrowY);
		} else if( heightDiff <= 0 ){
			//之上
			var turn1 = {
				x: fromX + 30,
				y: fromY
			};
			var turn2 = {
				x: turn1.x,
				y: toY
			};

			ctx.moveTo(fromX, fromY);
			ctx.lineTo(turn1.x, turn1.y);

			ctx.moveTo(turn1.x, turn1.y);
			ctx.lineTo(turn2.x, turn2.y);

			ctx.moveTo(turn2.x, turn2.y);
			ctx.lineTo(toX, toY);

			//此时终点横坐标与第二个转折点的横坐标一直，故箭头向右
			angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
			angle1 = (angle + theta) * Math.PI / 180;
			angle2 = (angle - theta) * Math.PI / 180;
			topX = headlen * Math.cos(angle1);
			topY = headlen * Math.sin(angle1);
			botX = headlen * Math.cos(angle2);
			botY = headlen * Math.sin(angle2);

			arrowX = fromX - topX;
			arrowY = fromY - topY;
			ctx.moveTo(arrowX, arrowY);

			arrowX = toX + topX;
			arrowY = toY + topY;
			ctx.moveTo(arrowX, arrowY);
			ctx.lineTo(toX, toY);
			arrowX = toX + botX;
			arrowY = toY + botY;
			ctx.lineTo(arrowX, arrowY);
		}
	}

	ctx.strokeStyle = color;
	ctx.lineWidth = width;
	ctx.stroke();
	// ctx.closePath();

	ctx.restore();
}

function drawSameLeftArrow(ctx, fromX, fromY, toX, toY,theta,headlen,width,color,fromBox,toBox) {
	theta = typeof(theta) != 'undefined' ? theta : 30;
	headlen = typeof(theta) != 'undefined' ? headlen : 10;
	width = typeof(width) != 'undefined' ? width : 1;
	color = typeof(color) != 'color' ? color : '#000';

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


	ctx.save();
	ctx.beginPath();

	//起点和终点的高粗差
	var heightDiff = toY - fromY; //终点和起点的高度差
	var widthDiff = toX - fromX; //终点和起点的横坐标差

	if( widthDiff > 0 ) {

			var turn1 = {
				x: fromX - 30,
				y: fromY
			};
			var turn2 = {
				x: turn1.x,
				y:toY
			};

			ctx.moveTo(fromX, fromY);
			ctx.lineTo(turn1.x, turn1.y);

			ctx.moveTo(turn1.x, turn1.y);
			ctx.lineTo(turn2.x, turn2.y);

			ctx.moveTo(turn2.x, turn2.y);
			ctx.lineTo(toX, toY);

			//此时终点横坐标与第二个转折点的横坐标一直，故箭头向右
			angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
			angle1 = (angle + theta) * Math.PI / 180;
			angle2 = (angle - theta) * Math.PI / 180;
			topX = headlen * Math.cos(angle1);
			topY = headlen * Math.sin(angle1);
			botX = headlen * Math.cos(angle2);
			botY = headlen * Math.sin(angle2);

			arrowX = fromX - topX;
			arrowY = fromY - topY;
			ctx.moveTo(arrowX, arrowY);

			arrowX = toX + topX;
			arrowY = toY + topY;
			ctx.moveTo(arrowX, arrowY);
			ctx.lineTo(toX, toY);
			arrowX = toX + botX;
			arrowY = toY + botY;
			ctx.lineTo(arrowX, arrowY);

	} else if( widthDiff <= 0 ) {

			var turn1 = {
				x: toX - 30,
				y: fromY
			};
			var turn2 = {
				x: turn1.x,
				y: toY
			};

			ctx.moveTo(fromX, fromY);
			ctx.lineTo(turn1.x, turn1.y);

			ctx.moveTo(turn1.x, turn1.y);
			ctx.lineTo(turn2.x, turn2.y);

			ctx.moveTo(turn2.x, turn2.y);
			ctx.lineTo(toX, toY);

			//此时终点横坐标与第二个转折点的横坐标一直，故箭头向右
			angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
			angle1 = (angle + theta) * Math.PI / 180;
			angle2 = (angle - theta) * Math.PI / 180;
			topX = headlen * Math.cos(angle1);
			topY = headlen * Math.sin(angle1);
			botX = headlen * Math.cos(angle2);
			botY = headlen * Math.sin(angle2);

			arrowX = fromX - topX;
			arrowY = fromY - topY;
			ctx.moveTo(arrowX, arrowY);

			arrowX = toX + topX;
			arrowY = toY + topY;
			ctx.moveTo(arrowX, arrowY);
			ctx.lineTo(toX, toY);
			arrowX = toX + botX;
			arrowY = toY + botY;
			ctx.lineTo(arrowX, arrowY);
	}

	ctx.strokeStyle = color;
	ctx.lineWidth = width;
	ctx.stroke();
	// ctx.closePath();

	ctx.restore();
}

function drawInArrow( ctx,theta,headlen,width,color,pointsArray ){
	drawPointsArrow(ctx,pointsArray,theta,headlen,width,color);
}

function drawInnerArrow( ctx,theta,headlen,width,color,pointsArray ) {

	// drawPointsArrow(ctx,pointsArray,theta,headlen,width,color)
	// theta = typeof(theta) != 'undefined' ? theta : 30;
	// headlen = typeof(theta) != 'undefined' ? headlen : 10;
	// width = typeof(width) != 'undefined' ? width : 1;
	// color = typeof(color) != 'color' ? color : '#000';
	//
	// var angle = 0,
	// 	angle1 = 0,
	// 	angle2 = 0,
	// 	topX = 0,
	// 	topY = 0,
	// 	botX = 0,
	// 	botY = 0,
	// 	arrowX = 0,
	// 	arrowY = 0;

	// ctx.save();
	// ctx.beginPath();
	//
	// ctx.moveTo(fromX, fromY);
	// ctx.lineTo(turn1.x, turn1.y);
	//
	// if( turn2 ) {
	// 	ctx.moveTo(turn1.x, turn1.y);
	// 	ctx.lineTo(turn2.x, turn2.y);
	//
	// 	if( turn3 ) {
	// 		ctx.moveTo(turn2.x, turn2.y);
	// 		ctx.lineTo(turn3.x, turn3.y);
	//
	// 		if( turn4 ) {
	// 			ctx.moveTo(turn3.x, turn3.y);
	// 			ctx.lineTo(turn4.x, turn4.y);
	//
	// 			ctx.moveTo(turn4.x, turn4.y);
	// 			ctx.lineTo(toX, toY);
	// 			angle = Math.atan2(turn4.y - toY, turn4.x - toX) * 180 / Math.PI;
	// 		} else {
	// 			ctx.moveTo(turn3.x, turn3.y);
	// 			ctx.lineTo(toX, toY);
	// 			angle = Math.atan2(turn3.y - toY, turn3.x - toX) * 180 / Math.PI;
	// 		}
	// 	} else{
	// 		ctx.moveTo(turn2.x, turn2.y);
	// 		ctx.lineTo(toX, toY);
	// 		angle = Math.atan2(turn2.y - toY, turn2.x - toX) * 180 / Math.PI;
	// 	}
	// }else {
	// 	ctx.moveTo(turn1.x, turn1.y);
	// 	ctx.lineTo(toX, toY);
	// 	angle = Math.atan2(turn1.y - toY, turn1.x - toX) * 180 / Math.PI;
	// }
	//
	// angle1 = (angle + theta) * Math.PI / 180;
	// angle2 = (angle - theta) * Math.PI / 180;
	// topX = headlen * Math.cos(angle1);
	// topY = headlen * Math.sin(angle1);
	// botX = headlen * Math.cos(angle2);
	// botY = headlen * Math.sin(angle2);
	//
	// arrowX = fromX - topX;
	// arrowY = fromY - topY;
	// ctx.moveTo(arrowX, arrowY);
	//
	// arrowX = toX + topX;
	// arrowY = toY + topY;
	// ctx.moveTo(arrowX, arrowY);
	// ctx.lineTo(toX, toY);
	// arrowX = toX + botX;
	// arrowY = toY + botY;
	// ctx.lineTo(arrowX, arrowY);
	//
	// ctx.strokeStyle = color;
	// ctx.lineWidth = width;
	// ctx.stroke();
	// // ctx.closePath();
	//
	// ctx.restore();
}

function drawMelodyArrow( ctx, curLineDetail, width, color, shadow ) {

	var theta =  30;
	var headlen =  10;

	var angle = 0,
		angle1 = 0,
		angle2 = 0,
		topX = 0,
		topY = 0,
		botX = 0,
		botY = 0,
		arrowX = 0,
		arrowY = 0;

	var turnPoints = curLineDetail.turnPoints;
	var fromPoint = curLineDetail.from;
	var toPoint = curLineDetail.to;

	//开始画第一条线
	ctx.beginPath();

	ctx.moveTo(fromPoint.x, fromPoint.y);

	if( turnPoints.length > 0 ) {
		for( var i = 0, len = turnPoints.length; i < len; i++ ){
			if( i == 0 ){
				ctx.lineTo(turnPoints[i].x, turnPoints[i].y);
			}
			if( i > 0 ){
				ctx.moveTo(turnPoints[i-1].x, turnPoints[i-1].y);
				ctx.lineTo(turnPoints[i].x, turnPoints[i].y);
			}
			if( i == ( len - 1 ) ){
				ctx.moveTo(turnPoints[i].x, turnPoints[i].y);
				ctx.lineTo(toPoint.x, toPoint.y);

				angle = Math.atan2(turnPoints[i].y - toPoint.y, turnPoints[i].x - toPoint.x) * 180 / Math.PI;
				angle1 = (angle + theta) * Math.PI / 180;
				angle2 = (angle - theta) * Math.PI / 180;
				topX = headlen * Math.cos(angle1);
				topY = headlen * Math.sin(angle1);
				botX = headlen * Math.cos(angle2);
				botY = headlen * Math.sin(angle2);

				arrowX = fromPoint.x - topX;
				arrowY = fromPoint.y - topY;
				ctx.moveTo(arrowX, arrowY);

				arrowX = curLineDetail.to.x + topX;
				arrowY = curLineDetail.to.y + topY;
				ctx.moveTo(arrowX, arrowY);
				ctx.lineTo(toPoint.x, toPoint.y);
				arrowX = toPoint.x + botX;
				arrowY = toPoint.y + botY;
				ctx.lineTo(arrowX, arrowY);
			}
		}
	} else {
		ctx.lineTo(toPoint.x, toPoint.y);

		angle = Math.atan2(fromPoint.y - toPoint.y, fromPoint.x - toPoint.x) * 180 / Math.PI;
		angle1 = (angle + theta) * Math.PI / 180;
		angle2 = (angle - theta) * Math.PI / 180;
		topX = headlen * Math.cos(angle1);
		topY = headlen * Math.sin(angle1);
		botX = headlen * Math.cos(angle2);
		botY = headlen * Math.sin(angle2);

		arrowX = fromPoint.x - topX;
		arrowY = fromPoint.y - topY;
		ctx.moveTo(arrowX, arrowY);

		arrowX = curLineDetail.to.x + topX;
		arrowY = curLineDetail.to.y + topY;
		ctx.moveTo(arrowX, arrowY);
		ctx.lineTo(toPoint.x, toPoint.y);
		arrowX = toPoint.x + botX;
		arrowY = toPoint.y + botY;
		ctx.lineTo(arrowX, arrowY);
	}
	ctx.strokeStyle = color;
	ctx.lineWidth = width;
	if( shadow ){
		ctx.shadowBlur=10;
		ctx.shadowColor="black";
	}else {
		ctx.shadowBlur=0;
	}
	ctx.stroke();
	ctx.closePath();

	//画第二条线
	ctx.beginPath();

	ctx.moveTo(fromPoint.x, fromPoint.y);

	if( turnPoints.length > 0 ){
		for( var j = 0, len = turnPoints.length; j < len; j++ ){
			if( j == 0 ){
				ctx.lineTo(turnPoints[j].x, turnPoints[j].y);
			}
			if( j > 0 ){
				ctx.moveTo(turnPoints[j-1].x, turnPoints[j-1].y);
				ctx.lineTo(turnPoints[j].x, turnPoints[j].y);
			}
			if( j == ( len - 1 ) ) {
				ctx.moveTo(turnPoints[j].x, turnPoints[j].y);
				ctx.lineTo(toPoint.x, toPoint.y);
			}
		}
	} else {
		ctx.lineTo(toPoint.x, toPoint.y);
	}

	ctx.strokeStyle = 'rgba(0,0,0,0)';
	ctx.lineWidth = width + 5;
	ctx.stroke();
	ctx.closePath();
}