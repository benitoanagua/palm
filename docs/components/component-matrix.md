# PALM Component Matrix

This matrix inventories exported Web Components. Rows marked `review` are explicit verification gaps, not claims of passing keyboard or accessibility coverage.

| Component                                    | Native semantics       | Keyboard                            | Focus               | Loading | Error                    | Visual | A11y   |
| -------------------------------------------- | ---------------------- | ----------------------------------- | ------------------- | ------- | ------------------------ | ------ | ------ |
| `pm-icon`                                    | SVG                    | n/a                                 | n/a                 | n/a     | n/a                      | ✓      | ✓      |
| `pm-button`                                  | button                 | ✓                                   | ✓                   | ✓       | n/a                      | ✓      | ✓      |
| `pm-icon-button`                             | button                 | ✓                                   | ✓                   | ✓       | n/a                      | ✓      | ✓      |
| `pm-input`                                   | input                  | ✓                                   | ✓                   | n/a     | ✓                        | ✓      | ✓      |
| `pm-textarea`                                | textarea               | ✓                                   | ✓                   | n/a     | ✓                        | ✓      | ✓      |
| `pm-checkbox`                                | checkbox               | ✓                                   | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-radio`                                   | radio                  | ✓                                   | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-switch`                                  | switch                 | ✓                                   | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-select`                                  | select                 | ✓                                   | ✓                   | n/a     | ✓                        | ✓      | ✓      |
| `pm-card`                                    | article/button pattern | ✓                                   | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-badge`                                   | status text            | n/a                                 | n/a                 | n/a     | n/a                      | ✓      | ✓      |
| `pm-spinner`                                 | status                 | n/a                                 | n/a                 | ✓       | n/a                      | ✓      | ✓      |
| `pm-progress`                                | progressbar            | n/a                                 | n/a                 | ✓       | n/a                      | ✓      | ✓      |
| `pm-skeleton`                                | status                 | n/a                                 | n/a                 | ✓       | n/a                      | ✓      | ✓      |
| `pm-toast-region`                            | status/alert queue     | n/a                                 | ✓ (returns focus)   | n/a     | n/a                      | ✓      | ✓      |
| `pm-menu` / `pm-menu-item`                   | menu/menuitem          | ✓ (arrow/Home/End/Esc)              | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-pagination`                              | nav                    | ✓                                   | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-breadcrumbs` / `pm-breadcrumb-item`      | nav/ol                 | n/a                                 | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-avatar`                                  | img/text fallback      | n/a                                 | n/a                 | n/a     | ✓ (image error)          | ✓      | ✓      |
| `pm-segmented-control` / `pm-segmented-item` | radiogroup/radio       | ✓                                   | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-slider`                                  | range input            | ✓ (native)                          | ✓                   | n/a     | n/a                      | ✓      | ✓      |
| `pm-combobox` / `pm-combobox-option`         | combobox/listbox       | ✓ (arrows/Home/End/Enter/Esc)       | ✓                   | n/a     | ✓                        | ✓      | ✓      |
| `pm-date-picker`                             | dialog/grid            | ✓ (arrows/PageUp/PageDown/Home/End) | ✓ (roving tabindex) | n/a     | ✓                        | ✓      | ✓      |
| `pm-time-picker`                             | time input (native)    | ✓ (native)                          | ✓                   | n/a     | ✓                        | ✓      | ✓      |
| `pm-file-upload`                             | button + file input    | ✓ (Enter/Space + native dialog)     | ✓                   | n/a     | ✓ (type/size validation) | ✓      | ✓      |
| pm-accordion                                 | disclosure group       | review                              | review              | n/a     | n/a                      | review | review |
| pm-accordion-item                            | disclosure             | review                              | review              | n/a     | n/a                      | review | review |
| pm-alert                                     | alert/status           | n/a                                 | n/a                 | n/a     | n/a                      | review | review |
| pm-data-table                                | table                  | review                              | review              | n/a     | n/a                      | review | review |
| pm-dialog                                    | dialog                 | review                              | review              | n/a     | n/a                      | review | review |
| pm-drawer                                    | dialog/region          | review                              | review              | n/a     | n/a                      | review | review |
| pm-empty-state                               | status/content         | n/a                                 | n/a                 | n/a     | n/a                      | review | review |
| pm-form-field                                | label + control        | review                              | review              | n/a     | ✓                        | review | review |
| pm-metric-card                               | article                | n/a                                 | n/a                 | n/a     | n/a                      | review | review |
| pm-nav                                       | nav                    | review                              | review              | n/a     | n/a                      | review | review |
| pm-nav-item                                  | link/button            | review                              | review              | n/a     | n/a                      | review | review |
| pm-stack                                     | layout container       | n/a                                 | n/a                 | n/a     | n/a                      | review | review |
| pm-status                                    | status tag             | n/a                                 | n/a                 | n/a     | n/a                      | review | review |
| pm-surface                                   | section/container      | n/a                                 | n/a                 | n/a     | n/a                      | review | review |
| pm-tab                                       | tab                    | review                              | review              | n/a     | n/a                      | review | review |
| pm-tab-panel                                 | tabpanel               | review                              | review              | n/a     | n/a                      | review | review |
| pm-tabs                                      | tablist                | review                              | review              | n/a     | n/a                      | review | review |
| pm-toolbar                                   | toolbar                | review                              | review              | n/a     | n/a                      | review | review |
| pm-tooltip                                   | tooltip                | review                              | review              | n/a     | n/a                      | review | review |
