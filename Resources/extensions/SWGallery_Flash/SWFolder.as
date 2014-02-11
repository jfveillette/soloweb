
class SWFolder {
    public var assetFolderID:String;
    var pics:Array;
    var counter = -1;	
    function SWFolder( node:XMLNode ) {
        assetFolderID = node.attributes.id;
        pics = new Array();
    }
    
    function addPicture( pic:SWPicture ) {
        pics.push( pic );
    }
    
    function nextPicture():SWPicture {
        counter++;
        if( counter > pics.length - 1 ) {
            counter = 0;
        }
        return pics[ counter ];
    }
    
    function previousPicture():SWPicture {
        counter--;
        if( counter < 0 ) {
            counter = pics.length - 1;
        }
        return pics[ counter ];
    }

    function nextPictureURL():String {
        var pic:SWPicture = nextPicture();
        return pic.url;
    }
    
    function previousPitureURL():String{
        var pic:SWPicture = previousPicture();
        return pic.url;
    }
}