class Dnload{
	/* DownLoad control class for Flash 8 player, See 'useage' function for details of use                            */
	/* Please retain Credit with code: "Originally Created by Justin L Mills,  JLM aT Justinfront DoT net"    */
	/* 2 September 2005 (prior to flash 8 IDE launch)  Version 1.0       */
	/* Many thanks to flash community for providing info   */
	
	var r;/*file reference*/
	var a_mc:MovieClip;/*arrow*/
	var f_str:String;/*file name*/
	var b_mc:MovieClip;/*bar*/
	var t_txt:TextField;/*text field*/
	var h_mc:MovieClip;/*holder*/
	var d_num:Number;/*depths*/
	var n_str:String;
	var browse8;/*bring fileReference class in at run time! */
	public function Dnload(_mc:MovieClip,file:String,x:Number,y:Number, fileReference){
		browse8=fileReference;
		h_mc=_mc.createEmptyMovieClip("mc",_mc.getNextHighestDepth());
		h_mc._x=x;
		h_mc._y=y;
		f_str=file;
		createDisplay();
		setbutton();
	}
	private function createTF(){
		h_mc.createTextField("info",/*depth*/d_num++,/*x*/0,/*y*/0,/*_width*/100,/*_height*/20);
		t_txt=h_mc["info"];
		var ftemp=f_str.split("/");
		n_str=ftemp[ftemp.length-1];
		trace(n_str);
		t_txt.textColor=0x0066CC;
		t_txt.text="download: "+n_str+" ";
		t_txt.autoSize="Left";
		
	}
	private function createArrow(){/*must create t_txt first*/
		//arrow shape
		a_mc=h_mc.createEmptyMovieClip("arrow", d_num++);
		a_mc.moveTo(0, -27);
		a_mc.beginFill(0xF0F000, 100);
		a_mc.lineTo(0, -27);
		a_mc.lineTo(13, 6);
		a_mc.lineTo(6, 6);
		a_mc.lineTo(6, 27);
		a_mc.lineTo(-6, 27);
		a_mc.lineTo(-6, 6);
		a_mc.lineTo(-13, 6);
		a_mc.lineTo(0, -27);
		a_mc.endFill();
		//modify position and make fat
		a_mc._yscale=-30;
		a_mc._xscale=90;
		a_mc._x = t_txt._x-a_mc._width/2;
		a_mc._y = t_txt._y+a_mc._height;
	}
	private function createBar(){/*must create t_txt first*/
		//loaderBar (draw background)
		b_mc=h_mc.createEmptyMovieClip("bar_txt",d_num++);
		b_mc.lineStyle(2,0x0066CC,100);
		b_mc.lineTo(t_txt._width,0);
		b_mc._x=t_txt._x;
		b_mc._y=t_txt._y+t_txt._height;
		//set loading presets
		b_mc.moveTo(0,0);
		b_mc.lineStyle(1,0x0000ff,100)
	}
	private function createDisplay(){
		(d_num==undefined)?d_num=h_mc.getNextHighestDepth(): d_num-=3;
		createTF();
		createArrow();
		createBar();
	}
	private function setbutton(){
		var here=this;
		h_mc.onRollOver=function(){here.rollover()};
		h_mc.onRollOut=function(){here.rollout()};
		h_mc.onRelease=function(){here.startDownLoad()};
	}
	private function rollover(){trace(this);
		new Color(a_mc).setRGB(0xFFa000);
	}
	private function rollout(){
		new Color(a_mc).setRGB(0xF0F000);
	}
	private function startDownLoad(){
		var here=this;
		//r = new flash.net.FileReference();
		var r=browse8;
		r.addListener({onComplete:function(f){here.onComplete(f)},
					onProgress:function(f,l,t){here.onProgress(f,l,t)},
					onCancel:function(f){here.onCancel(f)},
					onOpen:function(f){here.onOpen(f)},
					onSelect:function(f){here.onSelect(f)}});
		if (r.download(f_str)) {
			new Color(a_mc).setRGB(0x00FF00);
			t_txt.text="Choose location/name for "+n_str;
		} else {
			new Color(a_mc).setRGB(0xFF0000);
			t_txt.text="Error: "+f_str;
			delete h_mc.onRollOver;
			delete h_mc.onRollOut;
			delete h_mc.onPress;
		} 
		
	}
	private function reset(){
		createDisplay();
		setbutton();
	}
	private function onComplete(f){
		var here=this;
		b_mc._visible=false;
		t_txt.text="finished downloading "+ n_str+ " click here to allow re-download";
		h_mc.onRelease=function(){here.reset()};
		h_mc.onRollover=function(){here.a_mc._visible=false};
		delete a_mc.onEnterFrame;
	}
	private function onProgress(f, bytesLoaded:Number, bytesTotal:Number){
		var percent:Number=Math.round((bytesLoaded/bytesTotal)*100);
		b_mc.moveTo(0,0);
		b_mc.lineStyle(1,0xff0000,100);
		new Color(a_mc).setRGB(0xFF00FF);
		a_mc.onEnterFrame=function(){
			if(this.x==undefined){this.x=0}
			this._y+=Math.sin(this.x+=(3*Math.PI/180));
			};
		b_mc.lineTo(Math.round(percent*b_mc._width/100),0);
		t_txt.text="loading: " +f.name+" "+percent+" %";
	}
	private function onCancel(f){
		t_txt.text="canceled: " + n_str;
		createDisplay();
	}
	private function onOpen(f){
		//do nothing
	}
	private function onSelect(f){
		if(f==n_str){
			t_txt.text="download size: " + f.size;
		} else {
			t_txt.text="Save " + n_str +" as "+ f.name;
		}
	}
	private function useage(){
	//d=new Dnload(_root,"DnLoad.as",30,30,new flash.net.FileReference());
	}
}
