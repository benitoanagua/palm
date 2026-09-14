import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import '@palm/components';

@NgModule({ schemas: [CUSTOM_ELEMENTS_SCHEMA] })
export class PalmModule {}

export const PALM_CUSTOM_ELEMENTS_SCHEMA = CUSTOM_ELEMENTS_SCHEMA;
