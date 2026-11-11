import * as React from 'react';

export const Default = () => (
  <div style={{ padding: 'var(--pm-space-6)', maxWidth: 600, margin: '0 auto' }}>
    <h2 style={{ fontSize: 'var(--pm-type-h2-size)', marginBottom: 'var(--pm-space-4)' }}>
      Formulario de contacto
    </h2>
    <pm-form-field label="Nombre completo" hint="Introduce tu nombre y apellidos" required>
      <pm-input name="nombre" required placeholder="María García" />
    </pm-form-field>
    <pm-form-field label="Correo electrónico" hint="tú@ejemplo.com" required>
      <pm-input name="correo" type="email" required placeholder="tú@ejemplo.com" />
    </pm-form-field>
    <pm-form-field label="Teléfono">
      <pm-input name="telefono" type="tel" placeholder="+34 600 000 000" />
    </pm-form-field>
    <pm-form-field label="País">
      <pm-select name="pais" value="es" required>
        <option value="es">España</option>
        <option value="mx">México</option>
        <option value="ar">Argentina</option>
      </pm-select>
    </pm-form-field>
    <pm-form-field label="Mensaje">
      <pm-textarea name="mensaje" rows="4" placeholder="¿En qué podemos ayudarte?" />
    </pm-form-field>
    <pm-form-field label="Aceptar términos">
      <pm-checkbox name="terminos" label="Acepto los términos y condiciones" required />
    </pm-form-field>
    <div style={{ display: 'flex', gap: 'var(--pm-space-2)', marginTop: 'var(--pm-space-4)' }}>
      <pm-button variant="secondary" type="button">
        Cancelar
      </pm-button>
      <pm-button type="submit">Enviar mensaje</pm-button>
    </div>
  </div>
);

export default { Default };
