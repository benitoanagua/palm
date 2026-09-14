# Visual Quality

PALM visual regression is defined by PALM itself, not by comparison with another design system.

## Viewports

| Name | Width | Height |
|---|---:|---:|
| mobile | 320 | 800 |
| mobile-large | 375 | 812 |
| tablet | 768 | 1024 |
| desktop | 1440 | 900 |

## State coverage

Components are captured in the states that apply to them:

- default
- hover
- active
- focus-visible
- disabled
- loading
- error
- light theme
- dark theme
- reduced motion

## Baseline policy

Baselines are generated from Palm Storybook stories. They are deterministic artifacts and must only change when the visual contract intentionally changes.

## Reference demos (unchanged)

[`demos/`](./demos/) holds the five approved HTML demos that define what "correct" looks like for this system end to end. They are delivered as-is and are not regenerated from these baselines — they predate and inform the component contracts, not the other way around.

| File | Covers |
|---|---|
| `palm-demo-definitiva.html` | Main app shell: task cards, Hub with sync states, Omnibar, 3-level density |
| `palm-demo-componentes-activeadmin.html` | Breadcrumb, scopes, index table, batch actions, pagination, filters, detail view, form |
| `palm-demo-dashboard-activeadmin.html` | Stat callouts, recent activity, quick links, comments |
| `palm-demo-adicionales-activeadmin.html` | Offcanvas, accordion, action dropdown, tooltip, toast, simple confirm modal |
| `palm-demo-formularios-estados-activeadmin.html` | Login, empty state, dynamic nested form fields, CSV/JSON export, switches |

See [`../../palm-design-system.md`](../../palm-design-system.md) §4 for the full write-up of what each demo demonstrates and why.
