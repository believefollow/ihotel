import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CyCptypeDetailComponent } from './cy-cptype-detail.component';

describe('Component Tests', () => {
  describe('CyCptype Management Detail Component', () => {
    let comp: CyCptypeDetailComponent;
    let fixture: ComponentFixture<CyCptypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CyCptypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cyCptype: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CyCptypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CyCptypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cyCptype on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cyCptype).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
