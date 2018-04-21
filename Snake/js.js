canvas = document.getElementById("canvas");
ctx = canvas.getContext("2d");

GAME_ARRAY_SIZE = 32; // 64 * 64
WIDTH = canvas.width/GAME_ARRAY_SIZE;
HEIGHT = canvas.height/GAME_ARRAY_SIZE;

TICK_RATE = 1000 / 5; // default
debufInfo = "";


function chooseDifficulty(){
	difficulty = prompt("Enter difficulty(1-3):");
	switch(parseInt(difficulty)){
		case 1:
			TICK_RATE = 1000 / 10;
		break;
		case 2:
			TICK_RATE = 1000 / 20;
		break;
		case 3:
			TICK_RATE = 1000 / 30;
		break;
	}
}

gameIsAlive = true;
direction = 3; //1 = left 2 = up 3 = right 4 = down
gameObjects = [];
food = null;
pressed = false; // boolean type
score = 0;

function initialize(){
	chooseDifficulty();
	direction = 3; //1 = left 2 = up 3 = right 4 = down
	gameObjects = [];
	gameObjects.push(createObject(2,0,true));
	gameObjects.push(createObject(1,0));
	gameObjects.push(createObject(0,0));	
	food = createFood();
	score = 0;
}

initialize();
ctx.strokeStyle = "grey";
ctx.fillStyle = "blue";

food = createFood();

function mainLoop(){
	if(gameIsAlive){
		debugInfo = "";
		ctx.clearRect(0,0,canvas.width,canvas.height);
		pressed = false;
		tick();
		draw();
		setText(debugInfo);
	}
}

function eated(){
	head = gameObjects[0];
	if(head.x == food.x && head.y == food.y){
		food = createFood();
		gameObjects.push(createObject(gameObjects[1].x,gameObjects[1].y));
		score++;
		debugInfo += "Push:" + "true<br>";
	}
	else{
		debugInfo += "Push:" + "false<br>"
	}
}

function border(){
	head = gameObjects[0];
	if(head.x < 0){
		head.x = GAME_ARRAY_SIZE-1;
	}
	if(head.y < 0){
		head.y = GAME_ARRAY_SIZE-1;
	}
	if(head.x >= GAME_ARRAY_SIZE){
		head.x = 0;
	}
	if(head.y >= GAME_ARRAY_SIZE){
		head.y = 0;
	}
}

function isDead(){
	head = gameObjects[0];
	for(var i=1;i<gameObjects.length;i++){
		if(gameObjects[i].x == head.x && gameObjects[i].y == head.y){
			again = confirm("Game Over! Wanna start again?");
			if(again){
				initialize();
			}	
			else{
				// do nothing
				gameIsAlive = false;
			}
		}
	}
}

function tick(){
	for(var i=gameObjects.length-1;i>=1;i--){
		gameObjects[i].x = gameObjects[i-1].x;
		gameObjects[i].y = gameObjects[i-1].y;
	}
	switch(direction){
		case 1:
		gameObjects[0].x--;
		break;
		case 2:
		gameObjects[0].y--;
		break;
		case 3:
		gameObjects[0].x++;
		break;
		case 4:
		gameObjects[0].y++;
		break;
	}
	border();
	eated();
	debugInfo += "Head: " + gameObjects[0].x + " " + gameObjects[0].y + "<br>";
	debugInfo += "<h1>SCORE:"+score+"</h1>";

	isDead();
}

function draw(){
	for(var i=0;i<gameObjects.length;i++){
		gameObjects[i].draw();
	}

	food.draw();

}

function createObject(x,y,head){
	return {
		x:x,
		y:y,
		isHead:head,
		draw:function() {
			if(head){
				ctx.fillStyle = "red";
			}else{
				ctx.fillStyle = "blue";
			}
			ctx.fillRect(this.x*WIDTH,this.y*HEIGHT,WIDTH,HEIGHT);
		}
	};
}

function createFood(){
	x = Math.floor(Math.random()*GAME_ARRAY_SIZE);
	y = Math.floor(Math.random()*GAME_ARRAY_SIZE);
	//x = 20 //debug to delete
	//y = 0; //debug to delete
	return {
		x:x,
		y:y,
		draw:function(){
			setText(x + " " + y);

			ctx.beginPath();
			ctx.arc(x*HEIGHT+HEIGHT/2,y*HEIGHT+HEIGHT/2,HEIGHT/2,0,2*Math.PI);
			ctx.stroke();
		}
	};
}


window.onkeydown = function(event){
	code = event.keyCode;
	//setText(code);
	// boolean pressed is needed to restrict user to input only once per tick()
	switch(code){
		case 37:
		if(direction!=3 && !pressed){
			direction = 1;
		}
		break;
		case 38:
		if(direction!=4 && !pressed){
			direction = 2;
		}
		break;
		case 39:
		if(direction!=1 && !pressed){
			direction = 3;
		}
		break;
		case 40:
		if(direction!=2 && !pressed){
			direction = 4;
		}
		break;
	}
	pressed = true;
}

function setText(str){ // debug
	document.getElementById("output").innerHTML = str;
}


setInterval(mainLoop,TICK_RATE);