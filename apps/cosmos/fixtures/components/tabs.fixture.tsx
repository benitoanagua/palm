import * as React from 'react';

export const Default = () => (
  <pm-tabs value="a">
    <pm-tab value="a" active>
      Tab A
    </pm-tab>
    <pm-tab value="b">Tab B</pm-tab>
    <pm-tab value="c">Tab C</pm-tab>
    <pm-tab-panel value="a">Contenido del panel A</pm-tab-panel>
    <pm-tab-panel value="b">Contenido del panel B</pm-tab-panel>
  </pm-tabs>
);
export const SecondTab = () => (
  <pm-tabs value="b">
    <pm-tab value="a">Tab A</pm-tab>
    <pm-tab value="b" active>
      Tab B
    </pm-tab>
    <pm-tab-panel value="b">Contenido del panel B</pm-tab-panel>
  </pm-tabs>
);

export default { Default, SecondTab };
