import * as React from 'react';

export const Default = () => (
  <pm-accordion multiple={false}>
    <pm-accordion-item label="Primera sección" value="a" open>
      <p>Contenido de la primera sección.</p>
    </pm-accordion-item>
    <pm-accordion-item label="Segunda sección" value="b">
      <p>Contenido de la segunda sección.</p>
    </pm-accordion-item>
    <pm-accordion-item label="Tercera sección" value="c">
      <p>Contenido de la tercera sección.</p>
    </pm-accordion-item>
  </pm-accordion>
);
export const Multiple = () => (
  <pm-accordion multiple>
    <pm-accordion-item label="Sección A" value="a" open>
      <p>Varias secciones pueden estar abiertas a la vez.</p>
    </pm-accordion-item>
    <pm-accordion-item label="Sección B" value="b" open>
      <p>Este es otro panel abierto simultáneamente.</p>
    </pm-accordion-item>
  </pm-accordion>
);

export default { Default, Multiple };
