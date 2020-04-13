package com.sabir.training.rcp.treeviewer.model;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class FileStructureContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return (File[])inputElement;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return ((File)parentElement).listFiles();
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return ((File)element).getParentFile();
	}

	@Override
	public boolean hasChildren(Object element) {
		File file = (File) element;
        if (file.isDirectory()) {
            return true;
        }
        return false;
	}

}
