import { AcGameObject } from "./AcGameObject";
import { Wall } from "@/assets/scripts/Wall";
import { Snake } from "@/assets/scripts/Snake";


export class GameMap extends AcGameObject{
    constructor(ctx, parent){
        // ctx为画布， parent用来动态修改画布长宽
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0; // 每个正方形小格子的最大边长，用于画布

        this.row = 25;
        this.col = 16;
        this.walls = [];
        this.wallcnt = 30;

        this.snakes = [
            new Snake({id : 0, color:"#4876EC", r : this.row - 2, c : 1}, this),
            new Snake({id : 1, color:"#F94848", r : 1, c : this.col - 2}, this),
        ]
    }

    create_walls(){
        const g = [];
        for(let r = 0; r < this.row; r++){
            g[r] = [];
            for(let c = 0; c < this.col; c++){
                g[r][c] = false;
            }
        }

        // 给四周加上墙
        for(let r = 0; r < this.row; r++){
            g[r][0] = g[r][this.col - 1] = true;
        } 
        for(let c = 0; c < this.col; c++){
            g[0][c] = g[this.row - 1][c] =  true;
        }
        
        // 创建随机障碍物
        for(let i = 0; i < this.wallcnt; i++){
            for(let j = 0; j < 2000; j++){
                let r = parseInt(Math.random() * this.row);
                let c = parseInt(Math.random() * this.col);
                if(g[r][c] || g[this.row - 1 - r][this.col - 1 - c]) continue;
                if(r == this.row - 2 && c == 1 || r == 1 && c == this.col - 2) continue;
                g[r][c] = g[this.row - 1 - r][this.col - 1 - c] = true;
                break;
            }
        }
        const copy_g = JSON.parse(JSON.stringify(g)); // 深度copy
        if(!this.cheak_connectivity(copy_g,1,this.col-2,this.row-2,1)) return false;
        for(let r = 0; r < this.row; r++){
            for(let c = 0; c < this.col; c++){
                if(g[r][c]){
                    this.walls.push(new Wall(r,c, this));
                }
            }
        }
        return true;
    }

    // 检查连通性
    cheak_connectivity(g, sx, sy, tx, ty)
    {
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = true;
        let dx = [-1,0,1,0], dy=[0,1,0,-1];
        for(let i = 0; i < 4; i++){
            let x = sx + dx[i], y = sy + dy[i];
            if(!g[x][y] && this.cheak_connectivity(g,x,y,tx,ty)) return true;
        }
        return false;
    }

    cheak_valid(cell){
        // 检测目标位置是否合法,没有撞到两条蛇的身体和障碍物
        for(const wall of this.walls){
            if(wall.r === cell.r && wall.c === cell.c){
                return false;
            }
        }
        for(const snake of this.snakes){
            let k = snake.cells.length;
            if(!snake.cheak_tail_increasing()){
                // 蛇尾可以走
                k--;
            }
            for(let i = 0; i < k; i++){
                if(snake.cells[i].r === cell.r && snake.cells[i].c === cell.c) return false;
            }
            
        }
        return true;
    }

    start(){
        for(let i = 0; i < 1000; i++){
            if(this.create_walls()) break;
        }
        this.add_listening_events()
    }


    add_listening_events(){
        // 获取用户输入
        this.ctx.canvas.focus();
        const [snake0, snake1] = this.snakes;
        this.ctx.canvas.addEventListener("keydown", e => {
            if(e.key === 'w') snake0.set_direction(0);
            else if(e.key === 'd') snake0.set_direction(1);
            else if(e.key === 's') snake0.set_direction(2);
            else if(e.key === 'a') snake0.set_direction(3);
            else if(e.key === 'ArrowUp') snake1.set_direction(0);
            else if(e.key === 'ArrowRight') snake1.set_direction(1);
            else if(e.key === 'ArrowDown') snake1.set_direction(2);
            else if(e.key === 'ArrowLeft') snake1.set_direction(3);
        })
    }

    update_size(){
        this.L = parseInt(Math.min(this.parent.clientWidth / this.col, this.parent.clientHeight / this.row));
        this.ctx.canvas.width = this.L * this.col;
        this.ctx.canvas.height = this.L * this.row;
    }

    next_step(){
        // 让两条蛇进入下一回合
        for(const snake of this.snakes){
            snake.next_step();
        }
    }
    cheak_ready(){
        // 判断两条蛇是否都准备好下一回合了
        for(const snake of this.snakes){
            if(snake.status !== "idle") return false;
            if(snake.direction === -1) return false;
        }
        return true;
    }

    update(){
        this.update_size();
        this.render();
        if(this.cheak_ready()){
            this.next_step();
        }
    }
    render(){
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let r = 0; r < this.row; r++){
            for(let c = 0; c < this.col; c++){
                if((r + c) % 2 == 0){
                    this.ctx.fillStyle = color_even;
                }else{
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}


















