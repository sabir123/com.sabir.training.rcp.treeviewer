package com.sabir.training.rcp.treeviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import com.sabir.training.rcp.treeviewer.model.FileStructureContentProvider;
import com.sabir.training.rcp.treeviewer.model.Person;
import com.sabir.training.rcp.treeviewer.model.TreeContentProvider;

public class View extends ViewPart {
	public static final String ID = "com.sabir.training.rcp.treeviewer.view";

	@Inject IWorkbench workbench;
	
	private TreeViewer viewer;
	
	private class FirstNameLabelProvider extends ColumnLabelProvider {
		
		// getText method is used from super class ColumnLabelProvider

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		@Override
		public String getText(Object element) {
			return element == null ? "" : ((Person)element).getFirstName();
			
		}

	}

	private class LastNameLabelProvider extends ColumnLabelProvider {
		
		// getText method is used from super class ColumnLabelProvider
		@Override
		public String getText(Object element) {
			return element == null ? "" : ((Person)element).getLastName();
		}
	}

	private class FileNameLabelProvider extends ColumnLabelProvider {
		
		// getText method is used from super class ColumnLabelProvider
		
		@Override
		public Image getImage(Object obj) {
			if(obj instanceof File) {
				if(((File)obj).isDirectory()) {
					
					return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
				}
			}
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
		@Override
		public String getText(Object element) {
			return element == null ? "" : ((File)element).getName();
		}
	}	
	
	private class FileSizeLabelProvider extends ColumnLabelProvider{
		@Override
		public String getText(Object element) {
			
			long fileSize = ((File)element).length();
			String fileSizeStr = Long.toString(fileSize) ;
			return element == null ? "-1" : fileSizeStr;
			
		}
	}
	
	
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new FileStructureContentProvider());
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		
//		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
//		column.getColumn().setWidth(300);
//		column.getColumn().setText("First Name");
//		column.setLabelProvider(new FirstNameLabelProvider());
//		
//		TreeViewerColumn column2 = new TreeViewerColumn(viewer, SWT.NONE);
//		column2.getColumn().setWidth(300);
//		column2.getColumn().setText("Last Name");
//		column2.setLabelProvider(new LastNameLabelProvider());
		

		TreeViewerColumn fileNameColumn = new TreeViewerColumn(viewer, SWT.NONE);
		fileNameColumn.getColumn().setWidth(300);
		fileNameColumn.getColumn().setText("File Name");
		
		fileNameColumn.setLabelProvider(new FileNameLabelProvider());
		
		TreeViewerColumn fileSizeColumn = new TreeViewerColumn(viewer, SWT.NONE);
		fileSizeColumn.getColumn().setWidth(100);
		fileSizeColumn.getColumn().setText("Size");
		fileSizeColumn.setLabelProvider(new FileSizeLabelProvider());

		// Provide the input to the ContentProvider
		viewer.setInput(new File[] {new File("C:\\work\\Test") , new File("C:\\Work\\GIT")});
		
		// Create a menu manager and create context menu
        MenuManager menuManager = new MenuManager();
        Menu menu = menuManager.createContextMenu(viewer.getTree());
        // set the menu on the SWT widget
        viewer.getTree().setMenu(menu);
        // register the menu with the framework
        getSite().registerContextMenu(menuManager, viewer);

        // make the selection available to other views
        getSite().setSelectionProvider(viewer);
        // Set the sorter for the table

		
		
		viewer.getTree().addListener(SWT.Expand, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				System.out.println("View.createPartControl(...).new Listener() {...}.handleEvent()" );
			}
		});
		
		viewer.refresh();
				
		getSite().setSelectionProvider(viewer);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
			System.out.println(
						"View.createPartControl(...).new ISelectionChangedListener() {...}.selectionChanged()");
				System.out.println(event.getSelection().toString());
				
			}
		});
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public static List<Person> createDataModel() {
		List<Person> persons = new ArrayList<Person>();
		
	    Person p1 = new Person("Steve", "Large", 43);
		persons.add(p1);
		
		Person p1Child = new Person("Isabella","Large",10);
		p1Child.setParent(p1);
		p1.addChildren(p1Child);
		
		Person p2 = new Person("John", "Volkens", 35);
		persons.add(p2);
		
		Person p2Child = new Person("Abby","Volkens",4);
		p2Child.setParent(p2);
		p2.addChildren(p2Child);
		
		
		Person p3 = new Person("Kessler", "Parker", 29);
		persons.add(p3);
		
		Person p3Child = new Person("Katie","Parker",2);
		p3Child.setParent(p3);
		p3.addChildren(p3Child);
		
		Person p4 = new Person("Michael", "Dent", 40);
		persons.add(p4);
		
		Person p4Child = new Person("Isabella","Large",10);
		p4Child.setParent(p4);
		p4.addChildren(p4Child);
		
		Person p5 = new Person("Jonathan", "Cox", 33);
		persons.add(p5);
		
		Person p5Child = new Person("Isabella","Large",10);
		p5Child.setParent(p5);
		p5.addChildren(p5Child);
		
		Person p6 = new Person("Kris", "Klindworth", 33);
		persons.add(p6);
		
		Person p6Child = new Person("Isabella","Large",10);
		p6Child.setParent(p6);
		p6.addChildren(p6Child);
		
		Person p7 = new Person("Beth", "Nepote", 44);
		persons.add(p7);
		
		Person p7Child = new Person("Isabella","Large",10);
		p7Child.setParent(p1);
		p7.addChildren(p7Child);
		
		Person p8 = new Person("Sindhu", "Chitikela", 27);
		persons.add(p8);
		Person p8Child = new Person("Isabella","Large",10);
		p8Child.setParent(p8);
		p8.addChildren(p8Child);
		
		Person p9 = new Person("Mike", "Barns", 33);
		persons.add(p9);
		Person p9Child = new Person("Isabella","Large",10);
		p9Child.setParent(p9);
		p8.addChildren(p9Child);
		
		Person p10 = new Person("Sabir", "Pasha", 32);
		persons.add(p10);
		
		Person p10Child = new Person("Yusra","Sabir",1);
		p10Child.setParent(p10);
		p10.addChildren(p10Child);
		
		return persons;
	}

}