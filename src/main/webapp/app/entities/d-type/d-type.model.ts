export interface IDType {
  id?: number;
  typeid?: number;
  typename?: string;
  fatherid?: number;
  disabled?: number | null;
}

export class DType implements IDType {
  constructor(
    public id?: number,
    public typeid?: number,
    public typename?: string,
    public fatherid?: number,
    public disabled?: number | null
  ) {}
}

export function getDTypeIdentifier(dType: IDType): number | undefined {
  return dType.id;
}
