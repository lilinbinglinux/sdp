package com.sdp.servflow.pubandorder.serve.param;

import com.sdp.servflow.pubandorder.node.model.node.StartNode;

public class DefaultShowFactory extends ShowFactory {

    private DefaultShowFactory(){
        
    }
    private static class innerClass{
        private static final DefaultShowFactory factorty = new  DefaultShowFactory();
    }
    public final static DefaultShowFactory getSingleton(){
        return innerClass.factorty;
    }
    @Override
    protected String getParamString(StartNode node) {
        return new JsonFactory().getParamString(node);
    }
    
    
    
}
