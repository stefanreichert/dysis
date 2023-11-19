/**
 * ObservableProvider.java created on 14.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.databinding;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;

/**
 * Represents a generic provider for non observale POJOs.
 * 
 * @author Stefan Reichert
 */
public class ObservableAdapter<ObservableType> {

	public static <ObservableType> ObservableAdapter<ObservableType> createAdapter(
			ObservableType observable) {
		return new ObservableAdapter<ObservableType>(
				(ObservableType) observable, observable.getClass()
						.getDeclaredFields());
	}

	public static <ObservableType> ObservableAdapter<ObservableType> createAdapter(
			ObservableType observable, Class<ObservableType> clazz) {
		return new ObservableAdapter<ObservableType>(
				(ObservableType) observable, clazz.getDeclaredFields());
	}

	public static <ObservableType> ObservableAdapter<ObservableType> createObservableProvider(
			ObservableType observable, String[] fieldNames)
			throws SecurityException, NoSuchFieldException {
		Field[] fields = new Field[fieldNames.length];
		for (int index = 0; index < fields.length; index++) {
			fields[index] = observable.getClass().getField(fieldNames[index]);
		}
		return new ObservableAdapter<ObservableType>(observable, fields);
	}

	private Map<String, Object> valueMap;

	private ObservableType adaptedObservable;

	private IChangeListener changeListener;

	private List<IChangeListener> registeredChangeListeners = new ArrayList<IChangeListener>();

	private ObservableAdapter(ObservableType observable, Field[] fields) {
		super();
		this.adaptedObservable = observable;
		valueMap = new HashMap<String, Object>();
		changeListener = new IChangeListener() {
			/**
			 * @see org.eclipse.core.databinding.observable.IChangeListener#handleChange(org.eclipse.core.databinding.observable.ChangeEvent)
			 */
			public void handleChange(ChangeEvent event) {
				// delegate invocation
				for (IChangeListener listener : registeredChangeListeners) {
					listener.handleChange(event);
				}
			}
		};
		initObservedFields(fields);
	}

	private void initObservedFields(Field[] fields) {
		for (Field field : fields) {
			String fieldname = field.getName();
			Object observableValue;
			if (field.getType().isAssignableFrom(List.class)) {
				observableValue = PojoObservables.observeList(Realm
						.getDefault(), adaptedObservable, field.getName());
				((IObservableList) observableValue)
						.addChangeListener(changeListener);
			}
			else if (field.getType().isAssignableFrom(Set.class)) {
				observableValue = PojoObservables.observeSet(
						Realm.getDefault(), adaptedObservable, field.getName());
				((IObservableSet) observableValue)
						.addChangeListener(changeListener);
			}
			else if (!Modifier.isFinal(field.getModifiers())) {
				observableValue = PojoObservables.observeValue(
						adaptedObservable, field.getName());
				((IObservableValue) observableValue)
						.addChangeListener(changeListener);
			}
			else {
				continue;
			}
			valueMap.put(fieldname, observableValue);
		}
	}

	public void addChangeListener(IChangeListener changeListener) {
		assert changeListener != null : "IChangeListener must not be null."; //$NON-NLS-1$
		registeredChangeListeners.add(changeListener);
	}

	public void removeChangeListener(IChangeListener changeListener) {
		registeredChangeListeners.remove(changeListener);
	}

	/**
	 * @return the adaptedObservable
	 */
	public ObservableType getAdaptedObservable() {
		return adaptedObservable;
	}

	public IObservableList getObservableList(String fieldName) {
		return (IObservableList) valueMap.get(fieldName);
	}

	public IObservableSet getObservableSet(String fieldName) {
		return (IObservableSet) valueMap.get(fieldName);
	}

	public IObservableValue getObservableValue(String fieldName) {
		return (IObservableValue) valueMap.get(fieldName);
	}
}