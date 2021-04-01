import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DCktypeDetailComponent } from './d-cktype-detail.component';

describe('Component Tests', () => {
  describe('DCktype Management Detail Component', () => {
    let comp: DCktypeDetailComponent;
    let fixture: ComponentFixture<DCktypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DCktypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dCktype: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DCktypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DCktypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dCktype on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dCktype).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
