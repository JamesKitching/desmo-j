package desmoj.extensions.experimentation.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * GUI-component to manage the report view. Contains an
 * {@link URLTreePanel URLTreePanel} and a
 * {@link ReportStylerPanel ReportSytlerPanel}.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Gunnar Kiesel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

public class ReportsPanel extends JPanel {

	/** GUI Components */
	ReportStylerPanel reportStylerPanel = new ReportStylerPanel();

	URLTreePanel urlTreePanel = new URLTreePanel();

	/** Creates a new ReportsPanel */
	public ReportsPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Inits the GUI */
	private void jbInit() throws Exception {
		this.setLayout(new BorderLayout());
		urlTreePanel.createActionListener(reportStylerPanel);
		this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, urlTreePanel,
				reportStylerPanel), BorderLayout.CENTER);
	}

}