import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [multiple, setMultiple] = useFixtureInput('multiple', false);
  return (
    <pm-accordion multiple={multiple}>
      <pm-accordion-item value="seccion1" label="Primera sección">
        Contenido de la primera sección.
      </pm-accordion-item>
      <pm-accordion-item value="seccion2" label="Segunda sección">
        Contenido de la segunda sección.
      </pm-accordion-item>
      <pm-accordion-item value="seccion3" label="Tercera sección">
        Contenido de la tercera sección.
      </pm-accordion-item>
    </pm-accordion>
  );
};

export const Default = () => (
  <pm-accordion>
    <pm-accordion-item value="seccion1" label="Primera sección">
      Contenido de la primera sección.
    </pm-accordion-item>
    <pm-accordion-item value="seccion2" label="Segunda sección">
      Contenido de la segunda sección.
    </pm-accordion-item>
  </pm-accordion>
);
export const Multiple = () => (
  <pm-accordion multiple>
    <pm-accordion-item value="seccion1" label="Primera sección">
      Contenido de la primera sección.
    </pm-accordion-item>
    <pm-accordion-item value="seccion2" label="Segunda sección">
      Contenido de la segunda sección.
    </pm-accordion-item>
  </pm-accordion>
);

export default { Playground, Default, Multiple };
