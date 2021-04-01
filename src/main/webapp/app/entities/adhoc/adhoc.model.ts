export interface IAdhoc {
  id?: string;
  remark?: string;
}

export class Adhoc implements IAdhoc {
  constructor(public id?: string, public remark?: string) {}
}

export function getAdhocIdentifier(adhoc: IAdhoc): string | undefined {
  return adhoc.id;
}
