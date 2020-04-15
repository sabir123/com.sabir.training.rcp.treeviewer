package com.sabir.training.rcp.treeviewer.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowSelectionHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("ShowSelectionHandler.execute()");
		ISelection selection = HandlerUtil.getCurrentStructuredSelection(event);
        if (selection != null & selection instanceof IStructuredSelection) {
            IStructuredSelection structSelection = (IStructuredSelection) selection;
            for (Iterator<Object> iterator = structSelection.iterator(); iterator
                    .hasNext();) {
                Object element = iterator.next();
                System.out.println(element.toString());
            }
        }
        return null;
	}

}
