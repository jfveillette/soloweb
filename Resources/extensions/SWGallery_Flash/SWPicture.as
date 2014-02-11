class SWPicture {	public var url:String;	public var altText:String;	public var description:String;	public var fileName:String;	public var picHeight:Number;	public var picWidth:Number;
	
	function SWPicture( node:XMLNode ) {		url = node.attributes.url;		// fileName = node.attributes.fileName;		picHeight = parseInt(node.attributes.picHeight);		picWidth = parseInt(node.attributes.picWidth);	}
	
}