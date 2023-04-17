window.onload = function() {
    var canvas = document.getElementById('canvas'); 
    var context = canvas.getContext('2d');

    canvas.width = 70;
    canvas.height = 70;

    function Particle(radius, y, x, lineWidth)
    {   
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = "#9647FE";
        this.lineWidth = lineWidth;
        this.styleDessin = 2;

        this.move = function() { 
            var dateDev  = new Date();
            var unite  = (dateDev.getSeconds() + (dateDev.getMilliseconds()/1000)) * 2;
            var diviseur = 15;
            var texte = 30 - unite;

            context.beginPath();
            context.globalCompositeOperation = 'source-over';
            context.font = this.radius+"px Arial";
            context.textAlign = 'center';
            context.textBaseline = 'middle';
            context.fillStyle = 'white';
            context.fillText(Math.floor(texte),this.x,this.y);
            context.globalCompositeOperation = 'destination-over';
            context.lineWidth = this.lineWidth;
            context.fill();
            context.fillStyle = this.color;
            context.strokeStyle = this.color;
            
            //context.arc(this.x, this.y, this.radius, (Math.PI*1.5), (Math.PI) * (unite/diviseur) + (Math.PI * 1.5), true);
            context.arc(this.x, this.y, this.radius, (Math.PI*1.5), (Math.PI) * (unite/diviseur) + (Math.PI * 1.5), true);
            context.lineTo(this.x,this.y); 
            context.fill();
            context.closePath();
        };
    };
    var particle = new Particle(30, 30, 30, 5);

    function animate() {
        context.clearRect(0, 0, canvas.width, canvas.height);
        particle.move();
        requestAnimationFrame(animate);
    }

    animate(); 

}