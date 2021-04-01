export interface ICode1 {
  id?: number;
  code1?: string;
  code2?: string;
}

export class Code1 implements ICode1 {
  constructor(public id?: number, public code1?: string, public code2?: string) {}
}

export function getCode1Identifier(code1: ICode1): number | undefined {
  return code1.id;
}
