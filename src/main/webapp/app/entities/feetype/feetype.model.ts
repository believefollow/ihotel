export interface IFeetype {
  id?: number;
  feenum?: number;
  feename?: string;
  price?: number;
  sign?: number;
  beizhu?: string | null;
  pym?: string | null;
  salespotn?: number;
  depot?: string | null;
  cbsign?: number | null;
  ordersign?: number | null;
  hoteldm?: string | null;
  isnew?: number | null;
  ygj?: number | null;
  autosign?: string | null;
  jj?: number | null;
  hyj?: number | null;
  dqkc?: number | null;
}

export class Feetype implements IFeetype {
  constructor(
    public id?: number,
    public feenum?: number,
    public feename?: string,
    public price?: number,
    public sign?: number,
    public beizhu?: string | null,
    public pym?: string | null,
    public salespotn?: number,
    public depot?: string | null,
    public cbsign?: number | null,
    public ordersign?: number | null,
    public hoteldm?: string | null,
    public isnew?: number | null,
    public ygj?: number | null,
    public autosign?: string | null,
    public jj?: number | null,
    public hyj?: number | null,
    public dqkc?: number | null
  ) {}
}

export function getFeetypeIdentifier(feetype: IFeetype): number | undefined {
  return feetype.id;
}
