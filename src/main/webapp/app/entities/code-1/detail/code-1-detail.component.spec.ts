import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Code1DetailComponent } from './code-1-detail.component';

describe('Component Tests', () => {
  describe('Code1 Management Detail Component', () => {
    let comp: Code1DetailComponent;
    let fixture: ComponentFixture<Code1DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Code1DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ code1: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Code1DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Code1DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load code1 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.code1).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
