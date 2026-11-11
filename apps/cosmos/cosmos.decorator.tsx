/**
 * Decorator global del catálogo PALM (cosmos.decorator.tsx).
 *
 * Se aplica a TODOS los fixtures. Proporciona:
 *  - un contenedor consistente con fondo y tipografía de los tokens PALM,
 *  - un control de tema claro/oscuro que escribe `data-pm-theme` en `<html>`
 *    y pinta el fondo/texto de la página con las variables de `--pm-color-*`,
 *  - un ancho máximo opcional para que los fixtures de lectura no se estiren
 *    en pantallas muy anchas.
 *
 * React Cosmos expone automáticamente `useFixtureSelect`/`useFixtureInput` como
 * controles del panel de props, por lo que el tema es editable desde la UI.
 */
import * as React from 'react';
import { useFixtureSelect } from 'react-cosmos/client';

type Theme = 'light' | 'dark';

export default function PmDecorator({
  options,
  children,
}: {
  options?: { theme?: Theme };
  children: React.ReactNode;
}) {
  const [theme, setTheme] = useFixtureSelect<Theme>('theme', {
    options: ['light', 'dark'],
    defaultValue: (options?.theme ?? 'light') as Theme,
  });

  React.useEffect(() => {
    const html = document.documentElement;
    const body = document.body;
    html.dataset.pmTheme = theme;
    html.style.background = 'var(--pm-color-background)';
    html.style.color = 'var(--pm-color-text)';
    body.style.background = 'var(--pm-color-background)';
    body.style.color = 'var(--pm-color-text)';
  }, [theme]);

  return (
    <div
      style={{
        background: 'var(--pm-color-background)',
        color: 'var(--pm-color-text)',
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      <main
        style={{
          padding: 'var(--pm-space-6) var(--pm-space-4)',
          maxWidth: 'var(--pm-container-max-width)',
          margin: '0 auto',
          width: '100%',
          boxSizing: 'border-box',
          minHeight: '100vh',
          display: 'flex',
          flexDirection: 'column',
          gap: 'var(--pm-space-6)',
        }}
      >
        {children}
      </main>
    </div>
  );
}
