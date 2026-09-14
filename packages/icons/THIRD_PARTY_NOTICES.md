# Third-party notices

## Tabler Icons

`src/data/tabler-outline.json` is generated from the `tabler-nodes-outline.json` data
file shipped inside the `@tabler/icons` npm package.

- Project: https://tabler.io/icons
- License: MIT
- Copyright (c) 2020-2026 Paweł Kuna

```
MIT License

Copyright (c) 2020-2026 Paweł Kuna

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

A regeneration script lives at `tooling/validation/no-legacy-icons.mjs` (icon-name
validation) — if `@tabler/icons` is upgraded, regenerate `tabler-outline.json` from
the new package's `tabler-nodes-outline.json` and re-run `pnpm check:icons`.
