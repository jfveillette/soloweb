function setHasChanged() { 
  top.hasChanged = "true";
}

function refresh(anURL) {
  if( top.hasChanged == "true" ) {
    parent.tree.location.href = anURL;
    top.hasChanged = "false";
  }
}