import { AcGameObject } from "@/assets/scripts/AcGameObject";
import { Cell } from "@/assets/scripts/Cell";

export class Snake extends AcGameObject{
    constructor(info, gamemap){
        super();
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;
        //  存放蛇的身体，cells[0]存放蛇头
        this.cells = [new Cell(info.r, info.c)];
        
        this.speed = 5;
        this.direction = -1; // -1表示没有指令，0、1、2、3表示上右下左
        this.status = "idle"; // idle表示静止，move表示正在移动，die表示死了
        this.next_cell = null; // 下一步目标位置
        this.dr = [-1,0,1,0]; // 行偏移
        this.dc = [0,1,0,-1]; // 列偏移
        this.step = 0; // 蛇的步数
        this.eps = 1e-2; // 允许误差
        this.eye_direction = 0;
        if(this.id === 1) this.eye_direction = 2;

        this.eye_dx = [
            [-1,1],
            [1,1],
            [1,-1],
            [-1,-1]
        ];
        this.eye_dy = [
            [-1,-1],
            [-1,1],
            [1,1],
            [-1,1]
        ];
    }

    start(){

    }

    set_direction(d){
        this.direction = d;
    }
    next_step(){
        // 更新蛇的状态
        const d = this.direction;
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        this.eye_direction = d;
        this.direction = -1;
        this.step++;
        this.status = "move";
        
        const k = this.cells.length;
        for(let i = k; i > 0; i--){
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i-1]));
        }
    }

    cheak_tail_increasing(){
        // 检查当前回合蛇的长度是否增加
        if(this.step <= 10) return true;
        if(this.step % 3 === 1) return true;
        return false;
    }

    update_move(){
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const dis = Math.sqrt(dx * dx + dy * dy);

        if(dis < this.eps){
            this.cells[0] = this.next_cell; // 添加一个新蛇头
            this.next_cell = null;
            this.status = "idle"; // 走完了
            if(!this.cheak_tail_increasing()){
                // 不增加，蛇尾走完后倒数第一、二哥cell重合，pop一个
                this.cells.pop();
            }
        }else{
            const move_dis = this.speed * this.timedelta / 1000; // 每帧走的距离
            this.cells[0].x += move_dis * dx / dis;
            this.cells[0].y += move_dis * dy / dis;

            if(!this.cheak_tail_increasing()){
                // 不增加，蛇尾走
                const k = this.cells.length;
                const tail = this.cells[k-1], tail_target = this.cells[k-2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                tail.x += move_dis * tail_dx / dis;
                tail.y += move_dis * tail_dy / dis;
            }
        }
    }

    update(){
        this.render();
        if(this.status === 'move'){
            this.update_move();
        }
    }

    render(){
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;
        
        ctx.fillStyle = this.color;
        if(this.status === "die"){
            ctx.fillStyle = "white";
        }
        for(const cell of this.cells){
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }

        for(let i = 1; i < this.cells.length; i++){
            const a = this.cells[i-1], b = this.cells[i];
            if(Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps){
                continue;
            }
            if(Math.abs(a.x - b.x) < this.eps){
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y-b.y) * L);
            }else{
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        ctx.fillStyle = "black";
        for(let i = 0; i < 2; i++){
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L;
            ctx.beginPath();
            ctx.arc(eye_x,eye_y,L*0.07,0,Math.PI*2);
            ctx.fill();
        }

    }
}




