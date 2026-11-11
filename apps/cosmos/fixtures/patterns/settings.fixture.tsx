import * as React from 'react';
import { useFixtureSelect, useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [theme, setTheme] = useFixtureSelect('theme', {
    options: ['light', 'dark'],
    defaultValue: 'light',
  });
  const [notifications, setNotifications] = useFixtureInput('notifications', true);
  const [language, setLanguage] = useFixtureSelect('language', {
    options: ['es', 'en'],
    defaultValue: 'es',
  });
  return (
    <div
      style={{
        padding: 'var(--pm-space-6)',
        maxWidth: 'var(--pm-container-max-width)',
        margin: '0 auto',
      }}
    >
      <h2 style={{ fontSize: 'var(--pm-type-h2-size)', marginBottom: 'var(--pm-space-4)' }}>
        Ajustes
      </h2>
      <pm-form-field label="Tema">
        <pm-segmented-control value={theme}>
          <pm-segmented-item value="light">Claro</pm-segmented-item>
          <pm-segmented-item value="dark">Oscuro</pm-segmented-item>
        </pm-segmented-control>
      </pm-form-field>
      <pm-form-field label="Idioma">
        <pm-select name="idioma" value={language}>
          <option value="es">Español</option>
          <option value="en">Inglés</option>
        </pm-select>
      </pm-form-field>
      <pm-form-field label="Notificaciones">
        <pm-switch name="notificaciones" checked={notifications} label="Activar notificaciones" />
      </pm-form-field>
      <p style={{ color: 'var(--pm-color-text-muted)', fontSize: 'var(--pm-type-caption-size)' }}>
        Cambiar el tema aplica el selector de color del sistema a toda la página.
      </p>
    </div>
  );
};

export const Default = () => (
  <div
    style={{
      padding: 'var(--pm-space-6)',
      maxWidth: 'var(--pm-container-max-width)',
      margin: '0 auto',
    }}
  >
    <h2 style={{ fontSize: 'var(--pm-type-h2-size)', marginBottom: 'var(--pm-space-4)' }}>
      Ajustes
    </h2>
    <pm-form-field label="Tema">
      <pm-segmented-control value="light">
        <pm-segmented-item value="light">Claro</pm-segmented-item>
        <pm-segmented-item value="dark">Oscuro</pm-segmented-item>
      </pm-segmented-control>
    </pm-form-field>
    <pm-form-field label="Idioma">
      <pm-select name="idioma" value="es">
        <option value="es">Español</option>
        <option value="en">Inglés</option>
      </pm-select>
    </pm-form-field>
    <pm-form-field label="Notificaciones">
      <pm-switch name="notificaciones" checked label="Activar notificaciones" />
    </pm-form-field>
  </div>
);

export default { Playground, Default };
